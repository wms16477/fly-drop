package aphler.flydrop.enums;

public enum DropType {

    TEXT(1, "文本"),
    FILE(2, "文件");


    public final Integer typeKey;

    public final String typeName;

    DropType(Integer typeKey, String typeName) {
        this.typeKey = typeKey;
        this.typeName = typeName;
    }

}
