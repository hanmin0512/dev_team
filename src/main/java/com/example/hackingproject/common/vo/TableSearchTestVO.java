import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableSearchTestVO {
	private String searchKeyword;
	private String title;

	public TableSearchTestVO() {

	}

	public TableSearchTestVO(TableSearchTestVO tableSearchTestVO, String keyword) {
		this.searchKeyword = tableSearchTestVO.getSearchKeyword();
		this.title = tableSearchTestVO.getTitle();

		System.out.println(keyword);
	}
}