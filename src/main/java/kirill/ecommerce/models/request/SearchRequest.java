package kirill.ecommerce.models.request;

public class SearchRequest {
    private String keyword;

    public SearchRequest(String keyword){
        this.keyword = keyword;
    }

    public String getKeyword(){
        return this.keyword;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
}
