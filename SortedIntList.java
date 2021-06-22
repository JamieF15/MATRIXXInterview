import java.util.LinkedList;

/* I have prepared a simple java class that needs completing. 
 * 
 * I compiled and run it with Java 1.8, and deliberately chose not to use the Collections classes as otherwise the problem needs to be made 
 * much more complex.
 * 
 * The class is a very basic singly linked list, with a main method that exercises it and puts output to std out. 
 * 
 * The slight wrinkle is that I want the list to remain sorted (ascending) by integer value when we do insertion and deletion, 
 * I want to use a linked list structure as it is more efficient in the use cases I am interested in, and have provided a Node class for that
 * 
 * When we talk tomorrow, you will be able to screen share to show us what you have done, so use whatever development environment 
 * you like. Feel free to ask a friend, or browse for answers, I am trying to simulate a working environment not an exam.
 * 
 * The output to std out is clearly rubbish for testing, but I want you to concentrate on building a singly linked list of integers 
 * that keeps itself sorted by value, and get those methods filled in as cleanly as you can in an hour or so. 
 * 
 * Developing a proper test harness is something we can talk about, but I don’t want you to put effort into it.
 * 
 * Please have your development environment open before the interview, with the proposed changes in it, so they can be discussed
 */
public class SortedIntList {

  //stores the indexes of the removed elements when attempting to delete all of one key
  static LinkedList < Integer > removedElementIndexes = new LinkedList < Integer > ();

  // helper class to represent a node in a linked list
  // for the exercise you must use this singly linked list representation
  class IntNode {
    public Integer val;
    public IntNode next;

    public IntNode(int data) {
      val = data;
      next = null;
    }
  }

  private IntNode head = null;

  // return total number of nodes in the list
  int size() {

    //count variable 
    int count = 0;

    //loop until the next nodes does not point to null
    for (IntNode n = head; n != null; n = n.next) {

      //increment the counter
      count++;
    }

    //return the count of the list
    return count;
  }

  // return the index at which a value is found, -1 if it is not found
  int findFirst(int data) {

    //index of the key
    int index = 0;

    //check if the list is empty
    if (checkIfListIsEmpty()) {

      for (IntNode n = head; n != null; n = n.next) {

        //increment the index
        index++;

        //if the value of the node being iterated through has the value of the data, return the index
        if (n.val == data) {

          //return the index of the first element node that has the value of the data passed in 
          return index;

        }
      }

      //key not found when there the key is not in the list
      return -1;

    } else {

      //key not found when the list is empty
      return -1;

    }
  }

  // return the index at which value was found, -1 if it is not found
  int removeFirst(int data) {

    //the element to remove the element from
    int elementToRemove = this.findFirst(data) - 1;

    //temp node
    IntNode temp = head;

    //check if the list is empty or not
    if (checkIfListIsEmpty()) {

      //if the value of the head is equal to the data
      if (head.val == data) {

        //remove the first element of the list
        head = head.next;

        //when the head's value is not equal to the key
      } else {

        //loop through the list up to the element to remove
        for (int i = 0; i < elementToRemove - 1; i++) {

          //set the temp to what it points to
          temp = temp.next;

        }

        //set the node the temp points to to the one after the one it points to
        temp.next = temp.next.next;

      }

      //return the element that the element was removed at
      return elementToRemove + 1;

      //element not found
    } else {

      return -1;

    }
  }

  // return the index at which first value was found, -1 if it is not found
  // correct index count for removeALL.
  // a better version would assume that sorting is working correctly and then iterate through until value is found,
  //then continue until the value is not found pointing the firstvalues previous to the lastvalues next.
  int removeAll(int data) {

    //temp node set as the head
    IntNode temp = head;

    //previous node set to null
    IntNode prev = null;

    //index of the removed element
    int index = 0;

    //while the temp is not null and the value of the temp node is equal to the data 
    while (temp != null && temp.val == data) {

      //set the head to the temp's next node
      head = temp.next;

      //set the temp to the head
      temp = head;

      //increment the index
      index++;

      removedElementIndexes.add(index - 1);
      
    }

    //loop while there are nodes in the list of a certain key
    while (!allNodesOfACertainKeyDeleted(data)) {

      //due to the list being sorted, we know that the number after the key is either the key again or another number. 
      //because of this, we can increment after each removal to find where all of the keys indexes are
      int elementsDeleted = 0;

      //reset the index
      index = 0;

      //loop while the temp node is not null
      while (temp != null) {

        //loop while the temp node is not null and the value of the temp node is not equal to the key
        while (temp != null && temp.val != data) {

          //increment the index
          index++;

          //set the previous node to the temp
          prev = temp;

          //set the temp to the node it points
          temp = temp.next;
        }

        //if temp is null, return the index
        if (temp == null) {

          return index;
        }

        //set the previous' next node to the temp's next node
        prev.next = temp.next;

        //set the temp to the previous' next node
        temp = prev.next;

        //add the zero based indexes to the linked list
        removedElementIndexes.add(index + elementsDeleted);

        //increment the count of elements deleted
        elementsDeleted++;
      }
    }

    //return the index
    return index;
  }

  //checks if there are key elements of a certain key in the list
  boolean allNodesOfACertainKeyDeleted(int key) {

    //loop through the list 
    for (IntNode n = head; n != null; n = n.next) {

      //if any node has the value of the key, return false
      if (n.val == key) {

        return false;
      }

    }

    //if there are no nodes with the key as their value, return true
    return true;
  }

  // return the (zero based) index at which the new node is inserted
  int insert(int data) { //sort lowest first, find node value less than or equal to. Then insert in correct place.

    //new node to be inserted
    IntNode newNode = new IntNode(data);

    //index of the new node
    int index = 1;

    //check if the head is null or the value of the head is more than or equal to the value of the new node
    if (head == null || head.val >= newNode.val) {

      //point the new node to the head
      newNode.next = head;

      //set the head to the new node
      head = newNode;

      //return the index
      return index;
    }

    //current node
    IntNode current = head;

    //loop while the current node does not point to null and the node that the current node points to 
    //does not have a value greater than the value of the new node
    while (current.next != null && current.next.val < newNode.val) {

      //increment the index
      index++;

      //set the current node to the one it points to
      current = current.next;
    }

    //point the new node to the currents next node
    newNode.next = current.next;

    //point the current node to the new nod
    current.next = newNode;

    //increment the index
    index++;

    //return the index
    return index;
  }

  // simple method to aid initial work
  private void display() {

    if (head == null) {
      System.out.println("List is empty");
      return;
    }
    // now walk the list
    IntNode current = head;
    while (current != null) {
      //Prints each node by incrementing pointer    
      System.out.print(current.val + " ");
      current = current.next;
    }
    System.out.println();
  }

  private void status(int idx) {

    //account for zero based indexing
    if (idx != -1) {
      idx = idx - 1;
    }

    System.out.print("at: " + idx + " size: " + size() + " / ");
    display();
  }

  //prints the element indexs that (potentially) multiple keys are deleted from the list
  private void status(LinkedList < Integer > idx) {

    //string builder to store the elements from the linked list
    StringBuilder sb = new StringBuilder();

    //check if there are any elements in the linked list
    if (removedElementIndexes.size() != 0) {

      //loop through the linked list of indexes and add them to the string builder
      for (int i = 0; i < removedElementIndexes.size(); i++) {

        //if the element in the linked list of indexes is not the last one, add a comma and a space to the string builder
        if (!(i == removedElementIndexes.size() - 1)) {
          sb.append(removedElementIndexes.get(i) + ", ");

          //if the index is the last one, just add the index
        } else {
          sb.append(removedElementIndexes.get(i));
        }
      }

      //if the stringbuilder is empty, append the notation for the key not being found
    } else {

      sb.append("-1");
    }

    System.out.print("at: " + sb + " size: " + size() + " / ");
    display();
  }

  //checks if the list has any nodes in it
  private boolean checkIfListIsEmpty() {

    //if the head points to null, return true
    if (head.next != null) {

      return true;

    } else {

      //if the head does not point to null, return false
      return false;
    }
  }

  public static void main(String args[]) {

    SortedIntList sList = new SortedIntList();
    int idx = -1;
    sList.status(idx);

    idx = sList.insert(4);
    sList.status(idx);

    idx = sList.insert(2);
    sList.status(idx);

    idx = sList.insert(3);
    sList.status(idx);

    idx = sList.insert(2);
    sList.status(idx);

    idx = sList.insert(3);
    sList.status(idx);

    idx = sList.insert(3);
    sList.status(idx);

    idx = sList.insert(5);
    sList.status(idx);

    System.out.println("Inserts Done");

    idx = sList.findFirst(5);
    sList.status(idx);

    idx = sList.removeFirst(4);
    sList.status(idx);

    idx = sList.removeAll(3);
    sList.status(removedElementIndexes);

    System.out.println("Removes Done");

    idx = sList.findFirst(5);
    sList.status(idx);

    System.out.println("All Done");
  }
}