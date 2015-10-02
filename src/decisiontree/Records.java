/**
 *
 */

package decisiontree;

import java.util.*;


class Records {
  class Record {
    private Map<String, String> values;
    private boolean classifier;
  
    public Record(String[] attributeNames, String[] attributeValues,
                  boolean classifier) {
      assert(attributeNames.length == attributeValues.length);
      values = new HashMap<String, String>();

      for ( int i = 0 ; i < attributeNames.length ; i++ ) {
        values.put(attributeNames[i], attributeValues[i]);
      }
    
      this.classifier = classifier;
    }

    public Record(Map<String, String> attributes, boolean classifier) {
      this.classifier = classifier;
      this.values = attributes;
    }

    public Set<String> getAttributes() {
      return values.keySet();
    }

    public String getAttributeValue(String attribute) {
      return values.get(attribute);
    }

    public boolean matchesClass(boolean classifier) {
      return classifier == this.classifier;
    }
  }

  private List<Record> records;

  public Records() {
    records = new LinkedList<Record>();
  }
  
  public void add(String[] attributeNames, String[] attributeValues,
                  boolean classifier) {
    records.add(new Record(attributeNames, attributeValues, classifier));
  }

  public void add(Map<String, String> attributes, boolean classifier) {
    records.add(new Record(attributes, classifier));
  }

  /**
   * Returns the number of records where the attribute has the specified
   * 'decision' value
   */
  int countDecisions(String attribute, String decision) {
    int count = 0;

    for ( Record e : records) {
      if ( e.getAttributeValue(attribute).equals(decision) )
        count++;
    }

    return count;
  }

  /**
   * Returns a map from each attribute name to a set of all values used in the
   * records for that attribute.
   */
  public Map<String, Set<String> > extractDecisions() {
    Map<String, Set<String> > decisions = new HashMap<String, Set<String> >();

    for ( String attribute : extractAttributes() ) {
      decisions.put(attribute, extractDecisions(attribute));
    }

    return decisions;
  }

  public int countNegative(String attribute, String decision,
                           Map<String, String> attributes) {
    return countClassifier(false, attribute, decision, attributes);
  }
  
  public int countPositive(String attribute, String decision,
                           Map<String, String> attributes) {
    return countClassifier(true, attribute, decision, attributes);
  }
  
  public int countNegative(Map<String, String> attributes) {
    return countClassifier(false, attributes);
  }
  
  public int countPositive(Map<String, String> attributes) {
    return countClassifier(true, attributes);
  }
  
  public int count(String attribute, String decision, Map<String, String> attributes) {
    attributes = new HashMap(attributes);
    attributes.put(attribute, decision);

    return count(attributes);
  }
  
  public int count(Map<String, String> attributes) {
    int count = 0;

nextExample:
    for ( Record e : records) {
      for ( Map.Entry<String, String> attribute : attributes.entrySet() )
        if ( !(e.getAttributeValue(attribute.getKey()).equals(attribute.getValue())) )
          continue nextExample;

      // All of the provided attributes match the example.
      count++;
    }

    return count;
  }
  
  public int countClassifier(boolean classifier, Map<String, String> attributes) {
    int count = 0;

nextExample:
    for ( Record e : records) {
      for ( Map.Entry<String, String> attribute : attributes.entrySet() )
        if ( !(e.getAttributeValue(attribute.getKey()).equals(attribute.getValue())) )
          continue nextExample;

      // All of the provided attributes match the example.
      // If the example matches the classifier, then include it in the count.
      if ( e.matchesClass(classifier) )
        count++;
    }

    return count;
  }

  public int countClassifier(boolean classifier, String attribute,
                             String decision, Map<String, String> attributes) {
    attributes = new HashMap(attributes);
    attributes.put(attribute, decision);

    return countClassifier(classifier, attributes);
  }
  
  /**
   * Returns the number of records.
   */
  public int count() {
    return records.size();
  }

  /**
   * Returns a set of attribute names used in the records.
   */
  public Set<String> extractAttributes() {
    Set<String> attributes = new HashSet<String>();

    for ( Record e : records) {
      attributes.addAll(e.getAttributes());
    }

    return attributes;
  }

  private Set<String> extractDecisions(String attribute) {
    Set<String> decisions = new HashSet<String>();

    for ( Record e : records) {
      decisions.add(e.getAttributeValue(attribute));
    }

    return decisions;
  }
}
