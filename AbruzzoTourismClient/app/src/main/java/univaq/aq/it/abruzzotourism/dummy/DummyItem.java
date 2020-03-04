package univaq.aq.it.abruzzotourism.dummy;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */

  
  public class DummyItem {

    private String id;
    public String content;
    private String details;

    public DummyItem() {}

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
      public String toString() {
            return content;
        }
  }
