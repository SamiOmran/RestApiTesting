package enums;

/**
 * Enum to specify the Fields of GET response.
 */
public enum ResponseFields {
    STATUS{
        @Override
        public String toString() {
            return "status";
        }
    },USIERID{
        @Override
        public String toString() {
            return "userId";
        }
    },TITLE{
        @Override
        public String toString() {
            return "title";
        }
    },BODY{
        @Override
        public String toString() {
            return "body";
        }
    }
}
