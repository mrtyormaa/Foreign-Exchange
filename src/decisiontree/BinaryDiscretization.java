package decisiontree;// Created by mrtyormaa on 10/1/15.

import java.util.Map;

public class BinaryDiscretization implements Discretize{

    @Override
    public String discretizeData(String attributeName, float attributeValue) {
        switch (attributeName){
            case "meanbid":
                if (attributeValue > 97.88)
                    return "high";
                return "low";
            case "meanask":
                if (attributeValue > 97.92)
                    return "high";
                return "low";
            case "meandelta":
                if (attributeValue > 0.0411)
                    return "high";
                return "low";
            case "minbid":
                if (attributeValue > 97.5)
                    return "high";
                return "low";
            case "minask":
                if (attributeValue > 97.95)
                    return "high";
                return "low";
            case "mindelta":
                if (attributeValue > 0.03199768)
                    return "high";
                return "low";
            case "maxbid":
                if (attributeValue > 97.894)
                    return "high";
                return "low";
            case "maxask":
                if (attributeValue > 97.936)
                    return "high";
                return "low";
            case "maxdelta":
                if (attributeValue > 0.052)
                    return "high";
                return "low";
            default:
                return null;
        }
    }
}
