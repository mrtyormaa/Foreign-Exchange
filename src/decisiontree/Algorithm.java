package decisiontree;

import java.util.*;


public interface Algorithm {
  abstract public Attribute nextAttribute(Map<String, String> chosenAttributes, Set<String> usedAttributes);
}
