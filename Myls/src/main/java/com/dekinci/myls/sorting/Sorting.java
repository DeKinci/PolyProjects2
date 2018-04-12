package com.dekinci.myls.sorting;

import java.util.Objects;

public class Sorting {
    public enum Attribute {
        NAME,
        SIZE,
        DATE,
        FOLDER
    }

    public static class Order {
        private boolean reversed;
        private Attribute attribute;

        public static Order straight(Attribute attribute) {
            return new Order(attribute, false);
        }

        public static Order reversed(Attribute attribute) {
            return new Order(attribute, true);
        }

        boolean isReversed() {
            return reversed;
        }

        public Attribute getAttribute() {
            return attribute;
        }

        public static Order opposite(Order order) {
            return new Order(order.attribute, !order.reversed);
        }

        private Order(Attribute attribute, boolean reversed) {
            this.reversed = reversed;
            this.attribute = attribute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Order)) return false;
            var order = (Order) o;
            return reversed == order.reversed &&
                    attribute == order.attribute;
        }

        @Override
        public int hashCode() {
            return Objects.hash(reversed, attribute);
        }
    }
}
