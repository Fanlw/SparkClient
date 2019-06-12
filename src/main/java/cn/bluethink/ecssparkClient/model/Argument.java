package cn.bluethink.ecssparkClient.model;

/**
 * @author flw
 * @date 2018年9月6日 
 */
public class Argument {
    private String name;
    private String value;
    // Key Value成对出现，即若paired=true，则name和value都需要出现在参数表中
    // name="-t", value="hello", paired=true, 那么执行时就类似于:xx.exe -t hello; 若paired=false, 则为:xx.exe hello
    private Boolean paired;
    // 参数是否可选，这个在执行器中忽略
    private Boolean optional;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getPaired() {
        return paired;
    }

    public void setPaired(Boolean paired) {
        this.paired = paired;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
