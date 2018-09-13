package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.utils.ClassUtils;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 导出工具类
 */
public class ExportUtils {

    private static final Map<String, Map<String, String>> map = new HashMap<>();

    public static Map<String, String> getExportFields(Class clazz) {
        if (null != map.get(clazz.getName())) {
            return map.get(clazz.getName());
        }
        Field[] fields = ClassUtils.getFields(clazz);

        if (fields.length == 0) {
            map.put(clazz.getName(), new Hashtable<>(0));
            return getExportFields(clazz);
        }

        Map<String, String> m = new LinkedHashMap<>(fields.length);
        for (Field field : fields) {
            String name = CloumnUtils.getName(field);
            if (null == name) {
                continue;
            }
            m.put(field.getName(), name);
        }
        map.put(clazz.getName(), m);

        return getExportFields(clazz);
    }

    public static void export(
            HttpServletResponse response,
            String sheetName,
            String title,
            String memo,
            Map<String, String> titleMap,
            List<Map<String, Object>> content
    ) throws IOException {
        Set<String> set = titleMap.keySet();
        Iterator<String> s = set.iterator();
        String[] titles = new String[set.size()];
        int index = 0;
        while (s.hasNext()) {
            titles[index++] = s.next();
        }
        int titleLength = titleMap.size();
        short contentCellLength = (short) (titleLength + 1);
        int contentRowLength = 0;
        if (null != content) {
            contentRowLength = content.size();
        }

        // start
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet sheet = excel.createSheet(sheetName);
        for (int i = 1; i < titles.length + 2; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        HSSFCellStyle titleStyle = excel.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleLength));
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(35);
        HSSFCell title0 = row0.createCell(0);
        title0.setCellValue(title);
        title0.setCellStyle(titleStyle);

        HSSFCellStyle memoStyle = excel.createCellStyle();
        memoStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        memoStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, titleLength));
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(15);
        HSSFCell title1 = row1.createCell(0);
        title1.setCellValue(memo);
        title1.setCellStyle(memoStyle);

        HSSFRow titlesRow = sheet.createRow(2);
        HSSFCellStyle titlesStyle = excel.createCellStyle();
        titlesStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titlesStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        for (int i = 1; i < contentCellLength; i++) {
            HSSFCell cell = titlesRow.createCell(i);
            cell.setCellValue(titleMap.get(titles[i - 1]));
            cell.setCellStyle(titlesStyle);
        }

        HSSFRow contentRow = null;
        HSSFCellStyle contentStyle = excel.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        for (int i = 0; i < contentRowLength; i++) {
            contentRow = sheet.createRow(i + 3);
            HSSFCell cellIdex = contentRow.createCell(0);
            cellIdex.setCellValue(i + 1);
            cellIdex.setCellStyle(contentStyle);
            Map<String, Object> entity = content.get(i);
            for (int j = 1; j < titles.length + 1; j++) {
                HSSFCell cell = contentRow.createCell(j);
                cell.setCellStyle(contentStyle);
                Object value = entity.get(titles[j - 1]);
                if (null != value) {
                    cell.setCellValue(value.toString());
                }
            }

        }

        excel.write(response.getOutputStream());

    }

}
