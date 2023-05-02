package speedit.bookplate.config.QueryCounter;

public class QueryCounter {

    private int count;

    public void increase(){
        count++;
    }

    public int getCount(){
        return count;
    }

    public boolean isWarn(){
        return count>10;
    }


}
