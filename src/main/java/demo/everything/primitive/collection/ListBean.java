package demo.everything.primitive.collection;

/**
 * 实现Comparable<T>接口达到natural ordering目的
 * @author dywane.diao
 *
 */
public class ListBean implements Comparable<ListBean>{
	private int id;
	private String name;
	
	public ListBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ListBean [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int compareTo(ListBean o) {
		int otherId = o.id; 
		if (this.id>otherId) {
			return 1;
		}
		if (this.id<otherId) {
			return -1;
		}
		return 0;
	}
}
