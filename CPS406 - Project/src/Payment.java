public enum Payment {
    VISA {
        public String toString() {
            return "Visa";
        }
    },
    MASTERCARD {
        public String toString() {
            return "Mastercard";
        }
    },
    AMEX {
        public String toString() {
            return "Amex";
        }
    },
    DEBIT {
        public String toString() {
            return "Debit";
        }
    }
}
