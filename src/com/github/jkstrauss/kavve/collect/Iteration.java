package com.github.jkstrauss.kavve.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A class that represents a single iteration of an iteartor. It allows for
 *  tracking the index of the iterator, as well as the removal of the current 
 *  element.</p>
 * @author Joseph K. Strauss
 *
 * @param <V>
 */
public class Iteration<V> {
  
  private final IterationalIterator<V> iterationalIterator;
  
  private final V value;
  
  private final int index;
  
  private Iteration(IterationalIterator<V> iterationalIterator, V value, int index) {
    this.iterationalIterator = iterationalIterator;
    this.value = value;
    this.index = index;
  }
  
  public V getValue() {
    return value;
  }

  public int getIndex() {
    return index;
  }

  public boolean hasNext() {
    return iterationalIterator.hasNext();
  }
  
  public void remove() {
    removePreserveIndex();
    iterationalIterator.index--;
  }
  
  public void removePreserveIndex() {
    iterationalIterator.iterator.remove();
  }
  
  private static class IterationalIterator<V> implements Iterator<Iteration<V>> {
    
    private final Iterator<V> iterator;

    private int index = 0;
    
    private IterationalIterator(Iterator<V> iterator) {
      this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public Iteration<V> next() {
      return new Iteration<V>(this, iterator.next(), index++);
    }
    
  }
  
  public static <V> Iterator<Iteration<V>> iterationsIterator(Iterator<V> iterator){
    return new IterationalIterator<V>(iterator);
  }
  
  public static <V> Iterable<Iteration<V>> iterations(Iterable<V> iterable){
    return new Iterable<Iteration<V>>() {
      @Override
      public Iterator<Iteration<V>> iterator() {
        return Iteration.iterationsIterator(iterable.iterator());
      }      
    };
  }
  
  @SafeVarargs
  public static <V> Iterable<Iteration<V>> iterations(V... array){
    List<V> list = new ArrayList<>(array.length);
    for(V value: array){
      list.add(value);
    }
    return Iteration.iterations(list);
  }
}
