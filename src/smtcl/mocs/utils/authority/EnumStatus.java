package smtcl.mocs.utils.authority;


public enum EnumStatus {
	Valid {
        public void setValue (Long value) {
        }
        public Long getValue () {
            return Long.valueOf("1");
        }
        public void setDisplay (String display) {
        }
        public String getDisplay () {
            return "有效";
        }
    },
    Invalid {
        public void setValue (Long value) {
        }
        public Long getValue () {
            return Long.valueOf("0");
        }
        public void setDisplay (String display) {
        }
        public String getDisplay () {
            return "无效";
        }
    };

    public abstract void setValue (Long value);

    public abstract Long getValue ();

    public abstract void setDisplay (String display);

    public abstract String getDisplay ();

    public static EnumStatus parse (String value) {
        if (value == null) throw new IllegalArgumentException ("unknown value: " + value);
        try {
            int index = Integer.parseInt (value);
            if (index < 0 || index >= values ().length)
                throw new IllegalArgumentException ("illegal value: " + value);
            return values ()[index];
        } catch (Exception ex) {
            for (EnumStatus item : values ()) {
                if (item.getDisplay ().equalsIgnoreCase (value) || item.toString ().equalsIgnoreCase (value))
                    return item;
            }
            throw new IllegalArgumentException ("illegal value: " + value);
        }
    }
}
