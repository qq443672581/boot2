package cn.dlj1.cms.db.condition;

public class Between implements Cnd {

	private String field;

	private QueryType type = QueryType.BETWEEN;

	private Object left;

	private Object right;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public QueryType getType() {
		return type;
	}

	public void setType(QueryType type) {
		this.type = QueryType.BETWEEN;
	}

	public Object getLeft() {
		return left;
	}

	public void setLeft(Object left) {
		this.left = left;
	}

	public Object getRight() {
		return right;
	}

	public void setRight(Object right) {
		this.right = right;
	}

	public Between() {
	}
	public Between(String field, Object left, Object right) {
		super();
		this.field = field;
		this.left = left;
		this.right = right;
	}
	
	

}
