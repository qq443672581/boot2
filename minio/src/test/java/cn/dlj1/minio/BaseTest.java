package cn.dlj1.minio;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BaseTest {

    static MinioClient client;

    @BeforeClass
    public static void init() throws InvalidPortException, InvalidEndpointException {
        client = new MinioClient("http://jd.dlj1.cn:9000/","qq443672581","yxx@144830");

    }

    @Test
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {

        List<Bucket> buckets = client.listBuckets();

        for (Bucket bucket : buckets) {
            System.out.println(bucket.name());
        }

    }

    @Test
    public void test1() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        String objectUrl = client.getObjectUrl("test", "1.png");
        System.out.println(objectUrl);
    }

    @Test
    public void upload() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {


        for (int i = 0; i < 10; i++) {
            client.putObject("test","x-" + i,"/Users/fivewords/Downloads/x.jpg","image/jpg");
        }


    }

    public void remove() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        Iterator<Result<Item>> iterator = client.listObjects("test").iterator();
        while (iterator.hasNext()){
            client.removeObject("test", iterator.next().get().objectName());
        }

    }

}
