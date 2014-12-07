package com.github.jkstrauss.kavve.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class TreeNode<V> implements Supplier<V>, Iterable<TreeNode<V>> {
  
  private final V value;
  private final List<TreeNode<V>> children = new ArrayList<>();
  private TreeNode<V> parent;
  
  private TreeNode(V value) {
    this.value = value;
  }
  
  public static <V> TreeNode<V> create(V value) {
    return new TreeNode<>(value);
  }
  
  public TreeNode<V> addNode(V value) {
    TreeNode<V> node = new TreeNode<>(value);
    node.parent = this;
    children.add(node);
    return node;
  }
  
  public ImmutableList<TreeNode<V>> rootToThis() {
    return ImmutableList.copyOf(
        Iterables.concat(parent.rootToThis(), ImmutableList.of(this)));
  }

  @Override
  public V get() {
    return value;
  }

  @Override
  public Iterator<TreeNode<V>> iterator() {
    return Iterables.concat(ImmutableList.of(this), children).iterator();
  }
  
  public Stream<TreeNode<V>> stream() {
    return ImmutableList.copyOf(this).stream();
  }
  
  public static void main(String[] args) {
    TreeNode<String> node = new TreeNode<>("B");
    node.addNode("C").addNode("D");
    node.addNode("E");
    node.stream().map(TreeNode::get).forEach(
        n -> System.out.println(n)
    );
  }
  
  
  
}
