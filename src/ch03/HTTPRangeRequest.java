package ch03;

public class HTTPRangeRequest {
    // 使用final能确保在对象引用被发布之前，final字段已经被正确初始化
    private final Range range;
    private String url;

    public HTTPRangeRequest(String url, int lowerBound, int upperBound) {
        this.url = url;
        this.range = new Range(lowerBound, upperBound);
    }

    public static class Range {
        private long lowerBound;
        private long upperBound;

        public Range(long lowerBound, long upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        public long getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(long lowerBound) {
            this.lowerBound = lowerBound;
        }

        public long getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(long upperBound) {
            this.upperBound = upperBound;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Range getRange() {
        return range;
    }
}
