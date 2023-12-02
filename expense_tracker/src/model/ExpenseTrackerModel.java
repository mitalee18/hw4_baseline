package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class represents a model which can add, remove, get transactions as well as
 * setMatchedFilterIndices and getMatchedFilterIndices for filtering the transactions which are stored in the transaction object
 * This class follows an observable design pattern and notifies the listeners whenever state is changed
 *
 * @author [Heather Conboy]
 * @see Transaction
 * @see ExpenseTrackerModelListener
 */
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;

  private List<ExpenseTrackerModelListener> observers;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    observers = new ArrayList<ExpenseTrackerModelListener>();
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      stateChanged();
  }

  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      if(listener != null){
         if(containsListener(listener)){
             return false; //if already registered return False
         }
          observers.add(listener);
          return true; //if not already registered the register and return False
      }
      return false;
  }

    /**
     * This is for testing the number of listeners
     * @return size of observers Arraylist
     */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      //TODO
      return observers.size();
  }

    /**
     * This is for testing purpose
     * @param listener The ExpenseTrackerModelListener to contain listener
     * @return True if listener exists in the observers ArrayList else returns False
     */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
      for(ExpenseTrackerModelListener observer: observers){
          if(observer.equals(listener)){
              return true;
          }
      }
      return false;
  }

    /**
     * Calls update method for every observer in the arraylist to update the view
     */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
      for(ExpenseTrackerModelListener observer: observers){
          observer.update(this);
      }
  }
}
