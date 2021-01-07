// If you edit this file, you must also edit its tests.
// For tests of this and the entire plume package, see class TestPlume.

package org.plumelib.util;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.determinism.qual.*;
import org.checkerframework.checker.index.qual.IndexFor;
import org.checkerframework.checker.index.qual.IndexOrHigh;
import org.checkerframework.checker.index.qual.IndexOrLow;
import org.checkerframework.checker.index.qual.LTLengthOf;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.SameLen;
import org.checkerframework.checker.interning.qual.PolyInterned;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.checkerframework.common.value.qual.ArrayLen;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.framework.qual.*;
import org.plumelib.reflection.ReflectionPlume;

import static org.plumelib.util.DeterminismUtils.*;

/**
 * Utilities for manipulating arrays and collections. This complements {@link java.util.Arrays} and
 * {@link java.util.Collections}.
 */
@SuppressWarnings({"interning"}) // to do later
@HasQualifierParameter(NonDet.class)
public final class ArraysPlume {
  /** This class is a collection of methods; it does not represent anything. */
  private ArraysPlume() {
    throw new Error("do not instantiate");
  }

  ///////////////////////////////////////////////////////////////////////////
  /// min, max
  ///

  // Could also add linear-time orderStatistics if I liked.

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") int min(int[] a) {
    return reduce(a, Integer::min);
  }

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") long min(long[] a) {
    return reduce(a, Long::min);
  }

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") double min(double[] a) {
    return reduce(a, Double::min);
  }

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Integer min(Integer[] a) {
    return reduce(a, (x,y) -> Integer.valueOf(Integer.min(x,y)));
  }

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Long min(Long[] a) {
    return reduce(a, (x,y) -> Long.valueOf(Long.min(x,y)));
  }

  /**
   * Return the smallest value in the array.
   *
   * @param a an array
   * @return the smallest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Double min(Double[] a) {
    return reduce(a, (x,y) -> Double.valueOf(Double.min(x,y)));
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") int max(int[] a) {
    return reduce(a, Integer::max);
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") long max(long[] a) {
    return reduce(a, Long::max);
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") double max(double[] a) {
    return reduce(a, Double::max);
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Integer max(Integer[] a) {
    return reduce(a, (x,y) -> Integer.valueOf(Integer.max(x,y)));
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Long max(Long[] a) {
    return reduce(a, (x,y) -> Long.valueOf(Long.max(x,y)));
  }

  /**
   * Return the largest value in the array.
   *
   * @param a an array
   * @return the largest value in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  public static @PolyDet("down") Double max(Double[] a) {
    return reduce(a, (x,y) -> Double.valueOf(Double.max(x,y)));
  }

  /**
   * Return a two-element array containing the smallest and largest values in the array.
   *
   * @param a an array
   * @return a two-element array containing the smallest and largest values in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings("determinism:invalid.array.component.type")  // TODO FIX
  public static @PolyDet("use") int @ArrayLen(2) [] minAndMax(int[] a) {
    return new @PolyDet("use") int [] {min(a), max(a)};
  }

  /**
   * Return a two-element array containing the smallest and largest values in the array.
   *
   * @param a an array
   * @return a two-element array containing the smallest and largest values in the array
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings("determinism:invalid.array.component.type")  // TODO FIX
  public static @PolyDet("down") long @ArrayLen(2) [] minAndMax(long[] a) {
    return new @PolyDet("down") long [] {min(a), max(a)};
  }

  /**
   * Return the difference between the smallest and largest array elements.
   *
   * @param a an array
   * @return the difference between the smallest and largest array elements
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for aggregation
  public static int elementRange(int[] a) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to elementRange(int[])");
    }
    int[] minAndMax = minAndMax(a);
    return minAndMax[1] - minAndMax[0];
  }

  /**
   * Return the difference between the smallest and largest array elements.
   *
   * @param a an array
   * @return the difference between the smallest and largest array elements
   * @throws ArrayIndexOutOfBoundsException if the array has length 0
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for aggregation
  public static long elementRange(long[] a) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to elementRange(long[])");
    }
    long[] minAndMax = minAndMax(a);
    return minAndMax[1] - minAndMax[0];
  }

  ///////////////////////////////////////////////////////////////////////////
  /// sum
  ///

  /**
   * Returns the sum of an array of integers.
   *
   * @param a an array
   * @return the sum of an array of integers
   */
  @Pure
  @StaticallyExecutable
  public static int sum(int[] a) {
    return reduce(a, Integer::sum);
  }

  /**
   * Returns the sum of all the elements of a 2d array of integers.
   *
   * @param a a 2d array
   * @return the sum of all the elements of a 2d array of integers
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for aggregation
  public static int sum(int[][] a) {
    int sum = 0;
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        sum += a[i][j];
      }
    }
    return sum;
  }

  /**
   * Returns the sum of an array of doubles.
   *
   * @param a an array
   * @return the sum of an array of doubles
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for aggregation
  public static double sum(double[] a) {
    double sum = 0;
    for (int i = 0; i < a.length; i++) {
      sum += a[i];
    }
    return sum;
  }

  /**
   * Returns the sum of all the elements of a 2d array of doubles.
   *
   * @param a a 2d array
   * @return the sum of all the elements of a 2d array of doubles
   */
  @Pure
  @StaticallyExecutable
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for aggregation
  public static double sum(double[][] a) {
    double sum = 0;
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        sum += a[i][j];
      }
    }
    return sum;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// indexOf
  ///

  /**
   * Searches for the first occurrence of the given element in the array, testing for equality using
   * the equals method.
   *
   * @param <T> type of the elements of the array
   * @param a an array
   * @param elt the element to search for
   * @return the first index whose element is equal to the specified element, or -1 if no such
   *     element is found in the array
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Pure
  public static <T> @PolyDet("up") int indexOf(
      @PolyDet("use") T @PolyDet [] a, @PolyDet @Nullable Object elt) {
    if (elt == null) {
      return indexOfEq(a, elt);
    }
    for (int i = 0; i < a.length; i++) {
      if (elt.equals(a[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array, testing for equality using
   * the equals method.
   *
   * @param <T> the type of the elements
   * @param a an array
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <= i <
   *     indexlimit}, or -1 if the element is not found in that section of the array
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Pure
  public static <T> @PolyDet("up") int indexOf(
      @PolyDet("use") T @PolyDet [] a,
      @PolyDet @Nullable Object elt,
      @PolyDet @IndexFor("#1") int minindex,
      @PolyDet @IndexOrHigh("#1") int indexlimit) {
    if (elt == null) {
      return indexOfEq(a, elt, minindex, indexlimit);
    }
    for (int i = minindex; i < indexlimit; i++) {
      if (elt.equals(a[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the list, testing for equality using
   * the equals method. Identical to List.indexOf, but included for completeness.
   *
   * @param a a list
   * @param elt the element to search for
   * @return the first index whose element is equal to the specified element, or -1 if no such
   *     element is found in the list
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Pure
  public static @PolyDet("up") int indexOf(
      @PolyDet List<? extends @PolyDet("use") @PolyNull Object> a, @PolyDet Object elt) {
    return a.indexOf(elt);
  }

  /**
   * Searches for the first occurrence of the given element in the list, testing for equality using
   * the equals method.
   *
   * @param a a list
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <= i <
   *     indexlimit}, or -1 if the element is not found in that section of the list
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(
      List<? extends @PolyNull Object> a,
      @Nullable Object elt,
      @IndexFor("#1") @NonNegative int minindex,
      @IndexOrHigh("#1") int indexlimit) {
    if (elt == null) {
      return indexOfEq(a, elt, minindex, indexlimit);
    }
    for (int i = minindex; i < indexlimit; i++) {
      if (elt.equals(a.get(i))) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array, testing for equality using
   * == (not the equals method).
   *
   * @param a an array
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOfEq(@PolyNull Object[] a, @Nullable Object elt) {
    for (int i = 0; i < a.length; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array, testing for equality using
   * == (not the equals method).
   *
   * @param a an array
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <= i <
   *     indexlimit}, or -1 if the element is not found in that section of the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOfEq(
      @PolyNull Object[] a,
      @Nullable Object elt,
      @IndexFor("#1") int minindex,
      @IndexOrHigh("#1") int indexlimit) {
    for (int i = minindex; i < indexlimit; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the list, testing for equality using
   * == (not the equals method).
   *
   * @param a a list
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOfEq(List<? extends @PolyNull Object> a, @Nullable Object elt) {
    for (int i = 0; i < a.size(); i++) {
      if (elt == a.get(i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the list, testing for equality using
   * == (not the equals method).
   *
   * @param a a list
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <= i <
   *     indexlimit}, or -1 if the element is not found in that section of the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOfEq(
      List<? extends @PolyNull Object> a,
      @Nullable Object elt,
      @IndexFor("#1") @NonNegative int minindex,
      @IndexOrHigh("#1") int indexlimit) {
    for (int i = minindex; i < indexlimit; i++) {
      if (elt == a.get(i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(int[] a, int elt) {
    for (int i = 0; i < a.length; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(long[] a, long elt) {
    for (int i = 0; i < a.length; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <=; i <
   *     indexlimit}, or -1 if the element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(
      int[] a, int elt, @IndexFor("#1") int minindex, @IndexOrHigh("#1") int indexlimit) {
    for (int i = minindex; i < indexlimit; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <=; i <
   *     indexlimit}, or -1 if the element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(
      long[] a, long elt, @IndexFor("#1") int minindex, @IndexOrHigh("#1") int indexlimit) {
    for (int i = minindex; i < indexlimit; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(boolean[] a, boolean elt) {
    for (int i = 0; i < a.length; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(double[] a, double elt) {
    for (int i = 0; i < a.length; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first occurrence of the given element in the array.
   *
   * @param a an array
   * @param elt the element to search for
   * @param minindex first index at which to search
   * @param indexlimit first index at which not to search
   * @return the first index i containing the specified element, such that {@code minindex <=; i <
   *     indexlimit}, or -1 if the element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   */
  @Pure
  public static int indexOf(
      boolean[] a, boolean elt, @IndexFor("#1") int minindex, @IndexOrHigh("#1") int indexlimit) {
    for (int i = minindex; i < indexlimit; i++) {
      if (elt == a[i]) {
        return i;
      }
    }
    return -1;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// indexOf, for finding subarrays
  ///

  // This is analogous to Common Lisp's "search" function.

  // This implementation is very inefficient; I could use tricky Boyer-Moore
  // search techniques if I liked, but it's not worth it to me yet.

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise,
   * testing for equality using the equals method.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first array, or -1 if no such
   *     element is found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(@PolyNull Object[] a, Object[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise,
   * testing for equality using == (not the equals method).
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first array, or -1 if the
   *     element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  // The signature on this method is unnecessarily strict because it
  // requires that the component types be identical.  The signature should
  // be indexOfEq(@PolyNull(1) Object[], @PolyNull(2) Object[]), but the
  // @PolyNull qualifier does not yet take an argument.
  @Pure
  public static int indexOfEq(@PolyNull Object[] a, @PolyNull Object[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarrayEq(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the list that matches the given array elementwise,
   * testing for equality using the equals method.
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first list, or -1 if no such
   *     element is found in the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(List<?> a, @PolyNull Object[] sub) {
    int aIndexMax = a.size() - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the list that matches the given array elementwise,
   * testing for equality using == (not the equals method).
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first list, or -1 if the
   *     element is not found in the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOfEq(List<?> a, @PolyNull Object[] sub) {
    int aIndexMax = a.size() - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarrayEq(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given list elementwise,
   * testing for equality using the equals method.
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second list starts in the first array, or -1 if no such
   *     element is found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(@PolyNull Object[] a, List<?> sub) {
    int aIndexMax = a.length - sub.size() + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given list elementwise,
   * testing for equality using == (not the equals method).
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second list starts in the first array, or -1 if the
   *     element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOfEq(@PolyNull Object[] a, List<?> sub) {
    int aIndexMax = a.length - sub.size() + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarrayEq(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the list that matches the given list elementwise, testing
   * for equality using the equals method.
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second list starts in the first list, or -1 if no such
   *     element is found in the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(List<?> a, List<?> sub) {
    int aIndexMax = a.size() - sub.size() + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the list that matches the given list elementwise, testing
   * for equality using == (not the equals method).
   *
   * @param a a list
   * @param sub subsequence to search for
   * @return the first index at which the second list starts in the first list, or -1 if the element
   *     is not found in the list
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOfEq(List<?> a, List<?> sub) {
    int aIndexMax = a.size() - sub.size() + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarrayEq(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first array, or -1 if the
   *     element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(int[] a, int[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first array, or -1 if the
   *     element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(double[] a, double[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index at which the second array starts in the first array, or -1 if the
   *     element is not found in the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(long[] a, long[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Searches for the first subsequence of the array that matches the given array elementwise.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @return the first index containing the specified element, or -1 if the element is not found in
   *     the array
   * @see java.util.ArrayList#indexOf(java.lang.Object)
   * @see java.lang.String#indexOf(java.lang.String)
   */
  @Pure
  public static int indexOf(boolean[] a, boolean[] sub) {
    int aIndexMax = a.length - sub.length + 1;
    for (int i = 0; i <= aIndexMax; i++) {
      if (isSubarray(a, sub, i)) {
        return i;
      }
    }
    return -1;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// mismatch
  ///

  // This is analogous to Common Lisp's "mismatch" function.

  // Put it off until later; for now, use the simpler subarray function,
  // which is a specialization of mismatch,

  ///////////////////////////////////////////////////////////////////////////
  /// subarray extraction
  ///

  // Note that the second argument is a length, not an end position.
  // That's to avoid confusion over whether it would be the last included
  // index or the first non-included index.

  /**
   * Return a subarray of the given array.
   *
   * @return a subarray of the given array
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   */
  @SideEffectFree
  public static @PolyNull @PolyInterned @PolyDet("use") Object @PolyDet [] subarray(
      @PolyNull @PolyInterned @PolyDet("use") Object @PolyDet [] a,
      @PolyDet("use") @NonNegative int startindex,
      @PolyDet("use") @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") int length) {
    @PolyNull @PolyInterned @PolyDet("use") Object @PolyDet [] result =
        new @PolyNull @PolyInterned @PolyDet("use") Object @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a sublist of the given list.
   *
   * @param <T> type of the elements
   * @param a the original list
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a sublist of the given list
   */
  @SideEffectFree
  public static <T> @PolyDet("up") List<@PolyDet("use") T> subarray(
      List<@PolyDet("use") T> a,
      @PolyDet("down") @IndexFor("#1") int startindex,
      @PolyDet("down") @IndexOrHigh("#1") int length) {
    return a.subList(startindex, startindex + length);
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [] subarray(
      @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [] a,
      @NonNegative @PolyDet int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet int length) {
    @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [] result =
        new @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") byte @PolyDet [] subarray(
      @PolyDet("use") byte @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") byte @PolyDet [] result = new @PolyDet("use") byte @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") boolean @PolyDet [] subarray(
      @PolyDet("use") boolean @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") boolean @PolyDet [] result = new @PolyDet("use") boolean @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") char @PolyDet [] subarray(
      @PolyDet("use") char @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") char @PolyDet [] result = new @PolyDet("use") char @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") double @PolyDet [] subarray(
      @PolyDet("use") double @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") double @PolyDet [] result = new @PolyDet("use") double @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") float @PolyDet [] subarray(
      @PolyDet("use") float @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") float @PolyDet [] result = new @PolyDet("use") float @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") int @PolyDet [] subarray(
      @PolyDet("use") int @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") int @PolyDet [] result = new @PolyDet("use") int @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") long @PolyDet [] subarray(
      @PolyDet("use") long @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") long @PolyDet [] result = new @PolyDet("use") long @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  /**
   * Return a subarray of the given array.
   *
   * @param a the original array
   * @param startindex the first index to be included
   * @param length the number of elements to include (not an end index, to avoid confusion over
   *     whether it would be the last included index or the first non-included index)
   * @return a subarray of the given array
   */
  @SideEffectFree
  public static @PolyDet("use") short @PolyDet [] subarray(
      @PolyDet("use") short @PolyDet [] a,
      @NonNegative @PolyDet("use") int startindex,
      @NonNegative @LTLengthOf(value = "#1", offset = "#2 - 1") @PolyDet("use") int length) {
    @PolyDet("use") short @PolyDet [] result = new @PolyDet("use") short @PolyDet [length];
    System.arraycopy(a, startindex, result, 0, length);
    return result;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// subarray testing
  ///

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using the equals method.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarray(
      @PolyNull Object[] a, @PolyNull Object[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (!Objects.equals(sub[i], a[aOffset + i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using == (not the equals method).
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarrayEq(
      @PolyNull Object[] a, @PolyNull Object[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using the equals method.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return the first index at which the second array starts in the first array, or -1 if no such
   *     element is found in the array
   */
  @Pure
  public static boolean isSubarray(@PolyNull Object[] a, List<?> sub, @NonNegative int aOffset) {
    if (aOffset + sub.size() > a.length) {
      return false;
    }
    for (int i = 0; i < sub.size(); i++) {
      if (!Objects.equals(sub.get(i), a[aOffset + i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using == (not the equals method).
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarrayEq(@PolyNull Object[] a, List<?> sub, @NonNegative int aOffset) {
    if (aOffset + sub.size() > a.length) {
      return false;
    }
    for (int i = 0; i < sub.size(); i++) {
      if (sub.get(i) != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using the equals method.
   *
   * @param a a list
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return the first index at which the second array starts in the first array, or -1 if no such
   *     element is found in the array
   */
  @Pure
  public static boolean isSubarray(List<?> a, @PolyNull Object[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.size()) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (!Objects.equals(sub[i], a.get(aOffset + i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using == (not the equals method).
   *
   * @param a a list
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarrayEq(List<?> a, @PolyNull Object[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.size()) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a.get(aOffset + i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using the equals method.
   *
   * @param a a list
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return the first index at which the second array starts in the first array, or -1 if no such
   *     element is found in the array
   */
  @Pure
  public static boolean isSubarray(List<?> a, List<?> sub, @NonNegative int aOffset) {
    if (aOffset + sub.size() > a.size()) {
      return false;
    }
    for (int i = 0; i < sub.size(); i++) {
      if (!Objects.equals(sub.get(i), a.get(aOffset + i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first, testing for equality using == (not the equals method).
   *
   * @param a a list
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarrayEq(List<?> a, List<?> sub, @NonNegative int aOffset) {
    if (aOffset + sub.size() > a.size()) {
      return false;
    }
    for (int i = 0; i < sub.size(); i++) {
      if (sub.get(i) != a.get(aOffset + i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarray(int[] a, int[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarray(long[] a, long[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarray(double[] a, double[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the second array is a subarray of the first, starting at the specified index
   * of the first.
   *
   * @param a an array
   * @param sub subsequence to search for
   * @param aOffset first index in {@code a} at which to search. Must be non-negative. The routine
   *     returns false if {@code aOffset} is too large to be a valid index for {@code a}.
   * @return true iff sub is a contiguous subarray of a
   */
  @Pure
  public static boolean isSubarray(boolean[] a, boolean[] sub, @NonNegative int aOffset) {
    if (aOffset + sub.length > a.length) {
      return false;
    }
    for (int i = 0; i < sub.length; i++) {
      if (sub[i] != a[aOffset + i]) {
        return false;
      }
    }
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// concatenation
  ///

  // Concat used to return null if both arguments are null.  That is
  // convenient for the implementer, but not so good for clients.

  /**
   * Call this method in order to suppress compiler warnings.
   *
   * @param <T> the base type of the result
   * @param lst the list to convert to an array
   * @return the result of lst.toArray, casted to a more precise type than Object[]
   */
  private static <T> T[] toTArray(List<T> lst) {
    @SuppressWarnings("unchecked")
    T[] asArray = (T[]) lst.toArray();
    return asArray;
  }

  /**
   * A wrapper around a list or an array (or null). Avoids code duplication for arrays and lists, at
   * the cost of object construction and method calls.
   */
  private static class ListOrArray<T> {
    // At most one field is non-null.  If both are null, this object represents the null value.
    /** The array that this object wraps, or null. */
    T @Nullable [] theArray = null;
    /** The list that this object wraps, or null. */
    @Nullable List<T> theList = null;

    /**
     * Creates a ListOrArray that wraps an array.
     *
     * @param theArray the delegate that will be wrapped
     */
    ListOrArray(T @Nullable [] theArray) {
      this.theArray = theArray;
    }

    /**
     * Creates a ListOrArray that wraps a list.
     *
     * @param theList the delegate that will be wrapped
     */
    ListOrArray(@Nullable List<T> theList) {
      this.theList = theList;
    }

    /**
     * Return true if this represents a null value.
     *
     * @return true if this represents a null value
     */
    boolean isNull() {
      return theArray == null && theList == null;
    }

    /**
     * Return the size of the collection this represents.
     *
     * @return the size of the collection this represents
     */
    @NonNegative int size() {
      if (theArray != null) {
        return theArray.length;
      } else if (theList != null) {
        return theList.size();
      } else {
        throw new Error("both fields are null");
      }
    }

    /**
     * Returns true if this represents an empty collection.
     *
     * @return true if this represents an empty collection
     */
    boolean isEmpty() {
      if (theArray != null) {
        return theArray.length == 0;
      } else if (theList != null) {
        return theList.isEmpty();
      } else {
        throw new Error("both fields are null");
      }
    }

    /**
     * Return an array with the same contents as this.
     *
     * @return an array with the same contents as this
     */
    T[] toArray() {
      if (theArray != null) {
        return theArray;
      } else if (theList != null) {
        return toTArray(theList);
      } else {
        throw new Error("both fields are null");
      }
    }

    /**
     * Copy the contents of this into the given array, starting at the given index in the array.
     *
     * @param dest the destination array
     * @param destPos the index at which to start overwriting elements of {@code dest}
     */
    @SuppressWarnings({
      "lowerbound:argument.type.incompatible", // TODO: annotate for Index Checker
      "index:argument.type.incompatible", // TODO: annotate for Index Checker
      "determinism:assignment.type.incompatible" // Iteration over OrderNonDet collection for assigning into another
    })
    void copyInto(@PolyDet("use") T @PolyDet [] dest, int destPos) {
      if (theArray != null) {
        System.arraycopy(theArray, 0, dest, destPos, theArray.length);
      } else if (theList != null) {
        for (int i = destPos; i < theList.size(); i++) {
          @SuppressWarnings({
            "lowerbound:assignment.type.incompatible",
            "index:assignment.type.incompatible"
          }) // index checker has no list support
          @IndexFor("dest") int index = i + theList.size();
          dest[index] = theList.get(i);
        }
      } else {
        throw new Error("both fields are null");
      }
    }

    /**
     * Returns the least upper bound of the classes of the elements of this.
     *
     * @return the least upper bound of the classes of the elements of this
     */
    @SuppressWarnings(
        "determinism:argument.type.incompatible") // Cannot assign List<T extends @NonDet Object> to
    // List<? extends @Det Object> (theList)
    @Nullable @NonDet Class<?> leastUpperBound() {
      if (theArray != null) {
        return ReflectionPlume.leastUpperBound(theArray);
      } else if (theList != null) {
        return ReflectionPlume.leastUpperBound(theList);
      } else {
        throw new Error("both fields are null");
      }
    }
  }

  /**
   * Return an array that contains all the elements of both arguments, in order. Returns an existing
   * array if possible (when one argument is null or empty).
   *
   * @param <T> the type of the sequence elements
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  @SuppressWarnings(
      "determinism:argument.type.incompatible") // Iteration over OrderNonDet collection for concatenation with another
  public static <T> @PolyDet("use") T @PolyDet [] concat(
      @PolyDet("use") T @Nullable @PolyDet [] a, @PolyDet("use") T @Nullable @PolyDet [] b) {
    return concat(new @PolyDet ListOrArray<T>(a), new @PolyDet ListOrArray<T>(b));
  }

  /**
   * Return an array that contains all the elements of both arguments, in order. Returns an existing
   * array if the list argument is null or empty.
   *
   * @param <T> the type of the sequence elements
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  @SuppressWarnings(
      "determinism:argument.type.incompatible") // Iteration over OrderNonDet collection for concatenation with another
  public static <T> @PolyDet("use") T @PolyDet [] concat(
      T @Nullable @PolyDet [] a, @Nullable @PolyDet List<T> b) {
    return concat(new @PolyDet ListOrArray<T>(a), new @PolyDet ListOrArray<T>(b));
  }

  /**
   * Return an array that contains all the elements of both arguments, in order. Returns an existing
   * array if the list argument is null or empty.
   *
   * @param <T> the type of the sequence elements
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  @SuppressWarnings(
      "determinism:argument.type.incompatible") // Iteration over OrderNonDet collection for concatenation with another
  public static <T> @PolyDet("use") T @PolyDet [] concat(
      @PolyDet @Nullable List<@PolyDet("use") T> a, @PolyDet("use") T @Nullable @PolyDet [] b) {
    return concat(new @PolyDet ListOrArray<T>(a), new @PolyDet ListOrArray<T>(b));
  }

  /**
   * Return an array that contains all the elements of both arguments, in order.
   *
   * @param <T> the type of the sequence elements
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  @SuppressWarnings(
      "determinism:argument.type.incompatible") // Iteration over OrderNonDet collection for concatenation with another
  public static <T> @PolyDet("use") T @PolyDet [] concat(
      @Nullable @PolyDet List<@PolyDet("use") T> a, @Nullable @PolyDet List<@PolyDet("use") T> b) {
    return concat(new @PolyDet ListOrArray<T>(a), new @PolyDet ListOrArray<T>(b));
  }

  /**
   * Return an array that contains all the elements of both arguments, in order.
   *
   * @param <T> the type of the sequence elements
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  @SuppressWarnings(
      "determinism:return.type.incompatible") // Iteration over OrderNonDet collection for assigning into another
  private static <T> @PolyDet("use") T @PolyDet [] concat(
      @PolyDet ListOrArray<@PolyDet("use") T> a, @PolyDet ListOrArray<@PolyDet("use") T> b) {
    if (a.isNull() && b.isNull()) {
      @SuppressWarnings("unchecked")
      @PolyDet("use") T @PolyDet [] result =
          (@PolyDet("use") T @PolyDet []) new @PolyDet("use") Object @PolyDet [0];
      return result;
    } else if (a.isNull()) {
      return b.toArray();
    } else if (b.isNull()) {
      return a.toArray();
    }
    // Both a and b are non-null.
    else if (a.isEmpty()) {
      return b.toArray();
    } else if (b.isEmpty()) {
      return a.toArray();
    }
    // Both a and b are non-empty.
    else {
      int size = a.size() + b.size();
      // TODO: Fix.  We want an array of type exactly T.  This computes an estimate to T,
      // from the elements in the arrays.  It might be a subtype of T, though, which is incorrect.
      @SuppressWarnings("unchecked")
      Class<T> resultType =
          ReflectionPlume.leastUpperBound(
              (Class<T>) a.leastUpperBound(), (Class<T>) b.leastUpperBound());

      if (resultType == null) {
        throw new Error("All values are null, don't know how to create result array");
      }

      @SuppressWarnings("unchecked")
      T[] result = (T[]) Array.newInstance(resultType, size);

      a.copyInto(result, 0);
      b.copyInto(result, a.size());
      return result;
    }
  }

  // Note: PolyAll is not quite right.  Need to review.
  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first array to concatenate
   * @param b the second array to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [] concat(
      @PolyNull @PolyInterned @PolyDet("use") String @Nullable @PolyDet [] a,
      @PolyNull @PolyInterned @PolyDet("use") String @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") String @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyNull @PolyInterned @PolyDet("use") String @PolyDet [] result = new @PolyDet("use") String @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") byte @PolyDet [] concat(
      @PolyDet("use") byte @Nullable @PolyDet [] a, @PolyDet("use") byte @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") byte @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") byte @PolyDet [] result = new @PolyDet("use") byte @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") boolean @PolyDet [] concat(
      @PolyDet("use") boolean @Nullable @PolyDet [] a,
      @PolyDet("use") boolean @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") boolean @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") boolean @PolyDet [] result = new @PolyDet("use") boolean @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") char @PolyDet [] concat(
      @PolyDet("use") char @Nullable @PolyDet [] a, @PolyDet("use") char @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") char @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") char @PolyDet [] result = new @PolyDet("use") char @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") double[] concat(
      @PolyDet("use") double @Nullable @PolyDet [] a,
      @PolyDet("use") double @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") double @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") double @PolyDet [] result = new @PolyDet("use") double @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") float @PolyDet [] concat(
      @PolyDet("use") float @Nullable @PolyDet [] a,
      @PolyDet("use") float @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") float @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") float @PolyDet [] result = new @PolyDet("use") float @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") int @PolyDet [] concat(
      @PolyDet("use") int @Nullable @PolyDet [] a, @PolyDet("use") int @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") int @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") int @PolyDet [] result = new @PolyDet("use") int @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") long @PolyDet [] concat(
      @PolyDet("use") long @Nullable @PolyDet [] a, @PolyDet("use") long @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") long @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") long @PolyDet [] result = new @PolyDet("use") long @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  /**
   * Return an array that contains all the elements of both argument arrays, in order. Returns a new
   * array unless one argument is null, in which case it returns the other array.
   *
   * @param a the first sequence to concatenate
   * @param b the second sequence to concatenate
   * @return an array that concatenates the arguments
   */
  public static @PolyDet("use") short[] concat(
      @PolyDet("use") short @Nullable @PolyDet [] a,
      @PolyDet("use") short @Nullable @PolyDet [] b) {
    if (a == null) {
      if (b == null) {
        return new @PolyDet("use") short @PolyDet [0];
      } else {
        return b;
      }
    } else {
      if (b == null) {
        return a;
      } else {
        @PolyDet("use") short @PolyDet [] result = new @PolyDet("use") short @PolyDet [a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Printing
  ///

  /**
   * Returns a string representation of the contents of the specified array. The argument must be an
   * array or null. This just dispatches one of the 9 overloaded versions of {@code
   * java.util.Arrays.toString()}.
   *
   * @param a an array
   * @return a string representation of the array
   * @throws IllegalArgumentException if a is not an array
   */
  @SideEffectFree
  public static @NonDet String toString(Object a) {
    if (a == null) {
      return "null";
    } else if (a instanceof boolean[]) {
      return Arrays.toString((boolean[]) a);
    } else if (a instanceof byte[]) {
      return Arrays.toString((byte[]) a);
    } else if (a instanceof char[]) {
      return Arrays.toString((char[]) a);
    } else if (a instanceof double[]) {
      return Arrays.toString((double[]) a);
    } else if (a instanceof float[]) {
      return Arrays.toString((float[]) a);
    } else if (a instanceof int[]) {
      return Arrays.toString((int[]) a);
    } else if (a instanceof long[]) {
      return Arrays.toString((long[]) a);
    } else if (a instanceof short[]) {
      return Arrays.toString((short[]) a);
    } else if (a instanceof Object[]) {
      return Arrays.toString((Object[]) a);
    } else if (a instanceof List<?>) {
      // Handles lists, but this is not a documented feature
      return a.toString();
    } else {
      throw new IllegalArgumentException(
          "Argument is not an array, but has class " + a.getClass().getName());
    }
  }

  /**
   * Returns the length of the argument array.
   *
   * @param a an array
   * @return the length of the array
   * @throws IllegalArgumentException if obj is null or is not an array
   */
  @Pure
  public static @NonNegative int length(Object a) throws IllegalArgumentException {
    if (a == null) {
      throw new IllegalArgumentException("Argument is null");
    } else if (a instanceof boolean[]) {
      return ((boolean[]) a).length;
    } else if (a instanceof byte[]) {
      return ((byte[]) a).length;
    } else if (a instanceof char[]) {
      return ((char[]) a).length;
    } else if (a instanceof double[]) {
      return ((double[]) a).length;
    } else if (a instanceof float[]) {
      return ((float[]) a).length;
    } else if (a instanceof int[]) {
      return ((int[]) a).length;
    } else if (a instanceof long[]) {
      return ((long[]) a).length;
    } else if (a instanceof short[]) {
      return ((short[]) a).length;
    } else if (a instanceof Object[]) {
      return ((Object[]) a).length;
    } else {
      throw new IllegalArgumentException(
          "Argument is not an array, but has class " + a.getClass().getName());
    }
  }

  /**
   * Return a string representation of the array. The representation is patterned after that of
   * java.util.ArrayList.
   *
   * @param a an array
   * @return a string representation of the array
   * @see java.util.ArrayList#toString
   */
  @SideEffectFree
  public static String toString(@PolyNull Object @Nullable [] a) {
    return toString(a, false);
  }

  /**
   * Return a string representation of the array. The representation is patterned after that of
   * java.util.ArrayList. Furthermore, each element is quoted like a Java String.
   *
   * @param a an array
   * @return a string representation of the array, with the elements quoted
   * @see java.util.ArrayList#toString
   */
  @SideEffectFree
  public static String toStringQuoted(@PolyNull Object @Nullable [] a) {
    return toString(a, true);
  }

  /**
   * Return a string representation of the array. The representation is patterned after that of
   * java.util.ArrayList. Furthermore, if quoted is true, then each element is quoted like a Java
   * String.
   *
   * @param a an array
   * @param quoted whether to quote the array elements
   * @return a string representation of the array
   * @see java.util.ArrayList#toString
   */
  @SuppressWarnings({"allcheckers:purity", "lock"}) // side effect to local state (string creation)
  @SideEffectFree
  public static String toString(@PolyNull Object @Nullable [] a, boolean quoted) {
    if (a == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    if (a.length > 0) {
      sb.append(a[0]);
      for (int i = 1; i < a.length; i++) {
        sb.append(", ");
        if (quoted && a[i] instanceof String) {
          String elt = (String) a[i];
          sb.append('\"');
          sb.append(UtilPlume.escapeJava(elt));
          sb.append('\"');
        } else {
          sb.append(a[i]);
        }
      }
    }
    sb.append("]");
    return sb.toString();
  }

  /**
   * Return a string representation of the collection. The representation is patterned after that of
   * java.util.ArrayList.
   *
   * @param a a collection
   * @return a string representation of the collection
   * @see java.util.ArrayList#toString
   */
  @SideEffectFree
  public static String toString(@Nullable Collection<?> a) {
    return toString(a, false);
  }

  /**
   * Return a string representation of the collection. The representation is patterned after that of
   * java.util.ArrayList.
   *
   * @param a a collection
   * @return a string representation of the collection, with the elements quoted
   * @see java.util.ArrayList#toString
   */
  @SideEffectFree
  public static String toStringQuoted(@Nullable Collection<?> a) {
    return toString(a, true);
  }

  /**
   * Return a string representation of the collection. The representation is patterned after that of
   * java.util.ArrayList. The representation permits quoting (or not) of strings.
   *
   * @param a a collection
   * @param quoted whether to quote the collection elements that are Java strings
   * @return a string representation of the list
   * @see java.util.ArrayList#toString
   */
  @SuppressWarnings({"allcheckers:purity", "lock"}) // side effect to local state (string creation)
  @SideEffectFree
  public static String toString(@Nullable Collection<?> a, boolean quoted) {
    if (a == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    if (a.size() > 0) {
      Iterator<?> itor = a.iterator();
      sb.append(itor.next());
      while (itor.hasNext()) {
        sb.append(", ");
        Object elt = itor.next();
        if (quoted && elt instanceof String) {
          sb.append('\"');
          sb.append(UtilPlume.escapeJava((String) elt));
          sb.append('\"');
        } else {
          sb.append(elt);
        }
      }
    }
    sb.append("]");
    return sb.toString();
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Sortedness
  ///

  /**
   * Returns whether the array is sorted.
   *
   * @param a an array
   * @return true iff the array is sorted
   */
  @Pure
  public static boolean sorted(int[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i + 1] < a[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns whether the array is sorted.
   *
   * @param a an array
   * @return true iff the array is sorted
   */
  @Pure
  public static boolean sorted(long[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i + 1] < a[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns whether the array is sorted in descending order.
   *
   * @param a an array
   * @return true iff the array is sorted in descending order
   */
  @Pure
  public static boolean isSortedDescending(int[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i + 1] > a[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns whether the array is sorted in descending order.
   *
   * @param a an array
   * @return true iff the array is sorted in descending order
   */
  @Pure
  public static boolean isSortedDescending(long[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i + 1] > a[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") boolean @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Boolean> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Boolean n = Boolean.valueOf(a[i]);
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") byte @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Byte> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Byte n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") char @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Character> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Character n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") float @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Float> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Float n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") short @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Short> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Short n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") int @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Integer> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      Integer n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements. Equality checking uses {@link
   * Double#equals}.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") double @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Double> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to create the last element,
      // but that would make the code much less readable.
      Double n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") long @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Long> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      // Could be optimized not to create the last element,
      // but that would make the code much less readable.
      Long n = a[i];
      if (hs.contains(n)) {
        return false;
      }
      hs.add(n);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") String @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") String> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      if (hs.contains(a[i])) {
        return false;
      }
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      hs.add(a[i]);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param a an array
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean noDuplicates(@PolyDet("use") Object @PolyDet [] a) {
    @PolyDet("upDet") HashSet<@PolyDet("use") Object> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.length; i++) {
      if (hs.contains(a[i])) {
        return false;
      }
      // Could be optimized not to add the last element,
      // but that would make the code much less readable.
      hs.add(a[i]);
    }
    return true;
  }

  /**
   * Return true iff a does not contain duplicate elements.
   *
   * <p>The implementation uses O(n) time and O(n) space.
   *
   * @param <T> the type of the elements
   * @param a a list
   * @return true iff a does not contain duplicate elements
   */
  @SuppressWarnings({"allcheckers:purity", "lock"}) // side effect to local state (HashSet)
  @Pure
  public static <T extends @PolyDet("use") Object> @PolyDet("down") boolean noDuplicates(
      @PolyDet List<T> a) {
    @PolyDet("upDet") HashSet<T> hs = new @PolyDet("upDet") HashSet<>();
    for (int i = 0; i < a.size(); i++) {
      T elt = a.get(i);
      if (!hs.add(elt)) {
        return false;
      }
    }
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Arrays as partial functions of int->int
  ///

  /**
   * Returns true if the array is a permutation of [0..a.length).
   *
   * @param a an array, representing a function
   * @return true iff all elements of a are in [0..a.length) and a contains no duplicates.
   */
  @SuppressWarnings("allcheckers:purity") // side effect to local state (array)
  @Pure
  public static boolean fnIsPermutation(int[] a) {
    // In the common case we expect to succeed, so use as few loops as possible
    @PolyDet("use") boolean @PolyDet [] see = new @PolyDet("use") boolean @PolyDet [a.length];
    for (int i = 0; i < a.length; i++) {
      int n = a[i];
      if (n < 0 || n >= a.length || see[n]) {
        return false;
      }
      see[n] = true;
    }
    return true;
  }

  /**
   * Return true iff the array does not contain -1.
   *
   * @param a an array, representing a function
   * @return true iff no element of a maps to -1
   */
  @Pure
  public static boolean fnIsTotal(int[] a) {
    return indexOf(a, -1) == -1; // not found
  }

  /**
   * Return an array [0..length).
   *
   * @param length the length of the result
   * @return fresh array that is the identity function of the given length
   */
  public static int[] fnIdentity(@NonNegative int length) {
    @PolyDet("use") int @PolyDet [] result = new @PolyDet("use") int @PolyDet [length];
    for (int i = 0; i < length; i++) {
      result[i] = i;
    }
    return result;
  }

  /**
   * Requires that fnIsPermutation(a) holds.
   *
   * @param a the input permutation
   * @return fresh array which is the inverse of the given permutation
   * @see #fnIsPermutation(int[])
   */
  public static int[] fnInversePermutation(int[] a) {
    return fnInverse(a, a.length);
  }

  /**
   * Return the inverse of the given function, which is represented as an array.
   *
   * @param a an array representing a function from [0..a.length) to [0..arange); each element of a
   *     is between 0 (inclusive) and arange (exclusive)
   * @param arange length of the argument's range and the result's domain
   * @return function from [0..arange) to [0..a.length) that is the inverse of a
   * @throws IllegalArgumentException if a value of a is outside of arange
   * @exception UnsupportedOperationException when the function is not invertible
   */
  public static int[] fnInverse(int[] a, @NonNegative int arange) {
    @PolyDet("use") int @PolyDet [] result = new @PolyDet("use") int @PolyDet [arange];
    Arrays.fill(result, -1);
    for (int i = 0; i < a.length; i++) {
      int ai = a[i];
      if (ai < -1 || ai >= arange) {
        throw new IllegalArgumentException(String.format("Bad range value: a[%d]=%d", i, ai));
      }
      // ai is either -1 or a valid index
      if (ai >= 0) {
        if (result[ai] != -1) {
          throw new UnsupportedOperationException(
              String.format("Not invertible; a[%d]=%d and a[%d]=%d", result[ai], ai, i, ai));
        }
        result[ai] = i;
      }
    }
    return result;
  }

  /**
   * Return the composition of the given two functions, all of which are represented as arrays.
   *
   * @param a function from [0..a.length) to [0..b.length)
   * @param b function from [0..b.length) to range R
   * @return function from [0..a.length) to range R that is the composition of a and b
   */
  @SuppressWarnings({
    "determinism:assignment.type.incompatible" // Iteration over OrderNonDet collection for assigning into another
  })
  public static int @SameLen("#1") [] fnCompose(@IndexFor("#2") int[] a, int[] b) {
    @PolyDet("use") int @PolyDet("use") [] result = new @PolyDet("use") int @PolyDet("use") [a.length];
    for (int i = 0; i < a.length; i++) {
      result[i] = b[a[i]];
    }
    return result;
  }

  /**
   * Return the composition of the given two (possibly partial) functions, all of which are
   * represented as arrays.
   *
   * @param a function from [0..a.length) to [-1..b.length)
   * @param b function from [0..b.length) to range R
   * @return function from [0..a.length) to {range R} union {-1}, that is the composition of a and
   *     b.
   */
  @SuppressWarnings(
      "determinism:assignment.type.incompatible") // Iteration over OrderNonDet collection for assigning into another
  public static int @SameLen("#1") [] partialFnCompose(@IndexOrLow("#2") int[] a, int[] b) {
    @PolyDet("use") int @PolyDet [] result = new @PolyDet("use") int @PolyDet [a.length];
    for (int i = 0; i < a.length; i++) {
      int inner = a[i];
      if (inner == -1) {
        result[i] = -1;
      } else {
        result[i] = b[inner];
      }
    }
    return result;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Set operations, such as subset, unions, and intersections
  ///

  // This implementation is O(n^2) when the smaller really is a subset, but
  // might be quicker when it is not.  Sorting both sets has (minimum
  // and maximum) running time of Theta(n log n).
  /**
   * Return whether smaller is a subset of bigger.
   *
   * <p>The implementation is to use collections because we want to take advantage of HashSet's
   * constant time membership tests.
   *
   * @param smaller first set to test
   * @param bigger second set to test
   * @return true iff smaller is a subset of bigger
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean isSubset(
      @PolyDet("use") long @PolyDet [] smaller, @PolyDet("use") long @PolyDet [] bigger) {
    @PolyDet("upDet") Set<@PolyDet("use") Long> setBigger = new @PolyDet("upDet") HashSet<>();

    for (int i = 0; i < bigger.length; i++) {
      setBigger.add(bigger[i]);
    }

    for (int i = 0; i < smaller.length; i++) {
      Long elt = smaller[i];
      if (!setBigger.contains(elt)) {
        return false;
      }
    }

    return true;
  }

  // This implementation is O(n^2) when the smaller really is a subset, but
  // might be quicker when it is not.  Sorting both sets has (minimum
  // and maximum) running time of Theta(n log n).
  /**
   * Return whether smaller is a subset of bigger.
   *
   * <p>The implementation is to use collections because we want to take advantage of HashSet's
   * constant time membership tests.
   *
   * @param smaller first set to test
   * @param bigger second set to test
   * @return true iff smaller is a subset of bigger
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean isSubset(
      @PolyDet("use") double @PolyDet [] smaller, @PolyDet("use") double @PolyDet [] bigger) {
    @PolyDet("upDet") Set<@PolyDet("use") Double> setBigger = new @PolyDet("upDet") HashSet<>();

    for (int i = 0; i < bigger.length; i++) {
      setBigger.add(bigger[i]);
    }

    for (int i = 0; i < smaller.length; i++) {
      Double elt = smaller[i];
      if (!setBigger.contains(elt)) {
        return false;
      }
    }

    return true;
  }

  // This implementation is O(n^2) when the smaller really is a subset, but
  // might be quicker when it is not.  Sorting both sets has (minimum
  // and maximum) running time of Theta(n log n).
  /**
   * Return whether smaller is a subset of bigger.
   *
   * <p>The implementation is to use collections because we want to take advantage of HashSet's
   * constant time membership tests.
   *
   * @param smaller first set to test
   * @param bigger second set to test
   * @return true iff smaller is a subset of bigger
   */
  @SuppressWarnings({
    "allcheckers:purity",
    "lock", // side effect to local state (HashSet)
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  @Pure
  public static @PolyDet("down") boolean isSubset(
      @PolyDet("use") String @PolyDet [] smaller, @PolyDet("use") String @PolyDet [] bigger) {
    @PolyDet("upDet") Set<@PolyDet("use") String> setBigger = new @PolyDet("upDet") HashSet<>();

    for (int i = 0; i < bigger.length; i++) {
      setBigger.add(bigger[i]);
    }

    for (int i = 0; i < smaller.length; i++) {
      if (!setBigger.contains(smaller[i])) {
        return false;
      }
    }

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Array comparators
  ///

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()} (which tests reference equality).
   */
  public static final class IntArrayComparatorLexical implements Comparator<int[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    @Override
    public int compare(int[] a1, int[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        if (a1[i] != a2[i]) {
          return ((a1[i] > a2[i]) ? 1 : -1);
        }
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()} (which tests reference equality).
   */
  public static final class LongArrayComparatorLexical implements Comparator<long[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    @Override
    public int compare(long[] a1, long[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        if (a1[i] != a2[i]) {
          return ((a1[i] > a2[i]) ? 1 : -1);
        }
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()} (which tests reference equality).
   */
  public static final class DoubleArrayComparatorLexical
      implements Comparator<double[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    @Override
    public @PolyDet("up") int compare(double[] a1, double[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        int result = Double.compare(a1[i], a2[i]);
        if (result != 0) {
          return (result);
        }
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()}.
   */
  public static final class StringArrayComparatorLexical
      implements Comparator<String[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    // The signature on this method is unnecessarily strict because it
    // requires that the component types be identical.  The signature should
    // be compare(@PolyNull(1) String[], @PolyNull(2) String[]), but the
    // @PolyNull qualifier does not yet take an argument.
    @Override
    public @PolyDet("up") int compare(@PolyNull String[] a1, @PolyNull String[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        int tmp = 0;
        if ((a1[i] == null) && (a2[i] == null)) {
          tmp = 0;
        } else if (a1[i] == null) {
          tmp = -1;
        } else if (a2[i] == null) {
          tmp = 1;
        } else {
          tmp = a1[i].compareTo(a2[i]);
        }
        if (tmp != 0) {
          return (tmp);
        }
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical elements but are not equal according
   * to {@code equals()} (which tests reference equality).
   */
  public static final class ComparableArrayComparatorLexical<T extends Comparable<T>>
      implements Comparator<T[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    // The signature on this method is unnecessarily strict because it
    // requires that the component types be identical.  The signature should
    // be compare(@PolyNull(1) T[], @PolyNull(2) T[]), but the
    // @PolyNull qualifier does not yet take an argument.
    @Override
    public @PolyDet("up") int compare(@PolyNull T[] a1, @PolyNull T[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        T elt1 = a1[i];
        T elt2 = a2[i];
        // Make null compare smaller than anything else
        if ((elt1 == null) && (elt2 == null)) {
          continue;
        }
        if (elt1 == null) {
          return -1;
        }
        if (elt2 == null) {
          return 1;
        }
        int tmp = elt1.compareTo(elt2);
        if (tmp != 0) {
          return tmp;
        }
        // Check the assumption that the two elements are equal.
        assert elt1.equals(elt2);
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays lexically (element-by-element). If all shared elements are the same, but the
   * lengths differ, then the shorter array is considered less.
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain equal objects but are not equal according to
   * {@code equals()}.
   *
   * <p>Note: if toString returns a nondeterministic value, such as one that depends on the result
   * of {@code hashCode()}, then this comparator may yield different orderings from run to run of a
   * program.
   */
  public static final class ObjectArrayComparatorLexical
      implements Comparator<Object[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    // The signature on this method is unnecessarily strict because it
    // requires that the component types be identical.  The signature should
    // be compare(@PolyNull(1) Object[], @PolyNull(2) Object[]), but the
    // @PolyNull qualifier does not yet take an argument.
    @Override
    public @NonDet int compare(@PolyNull Object[] a1, @PolyNull Object[] a2) {
      if (a1 == a2) {
        return 0;
      }
      int len = Math.min(a1.length, a2.length);
      for (int i = 0; i < len; i++) {
        int tmp = objectComparator.compare(a1[i], a2[i]);
        if (tmp != 0) {
          return tmp;
        }
      }
      return a1.length - a2.length;
    }
  }

  /**
   * Compare two arrays first by length (a shorter array is considered less), and if of equal length
   * compare lexically (element-by-element).
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()}.
   */
  public static final class IntArrayComparatorLengthFirst
      implements Comparator<int[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays by length, then lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    @Override
    public int compare(int[] a1, int[] a2) {
      if (a1 == a2) {
        return 0;
      }
      if (a1.length != a2.length) {
        return a1.length - a2.length;
      }
      for (int i = 0; i < a1.length; i++) {
        if (a1[i] != a2[i]) {
          return ((a1[i] > a2[i]) ? 1 : -1);
        }
      }
      return 0;
    }
  }

  /**
   * Compare two arrays first by length (a shorter array is considered less), and if of equal length
   * compare lexically (element-by-element).
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical numbers but are not equal according to
   * {@code equals()}.
   */
  public static final class LongArrayComparatorLengthFirst
      implements Comparator<long[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays by length, then lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    @Override
    public int compare(long[] a1, long[] a2) {
      if (a1 == a2) {
        return 0;
      }
      if (a1.length != a2.length) {
        return a1.length - a2.length;
      }
      for (int i = 0; i < a1.length; i++) {
        if (a1[i] != a2[i]) {
          return ((a1[i] > a2[i]) ? 1 : -1);
        }
      }
      return 0;
    }
  }

  /**
   * Compare two arrays first by length (a shorter array is considered less), and if of equal length
   * compare lexically (element-by-element).
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical objects but are not equal according to
   * {@code equals()}.
   */
  public static final class ComparableArrayComparatorLengthFirst<T extends Comparable<T>>
      implements Comparator<T[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays by length, then lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    // The signature on this method is unnecessarily strict because it
    // requires that the component types be identical.  The signature should
    // be compare(@PolyNull(1) T[], @PolyNull(2) T[]), but the
    // @PolyNull qualifier does not yet take an argument.
    @Override
    public @PolyDet("up") int compare(@PolyNull T[] a1, @PolyNull T[] a2) {
      if (a1 == a2) {
        return 0;
      }
      if (a1.length != a2.length) {
        return a1.length - a2.length;
      }
      for (int i = 0; i < a1.length; i++) {
        T elt1 = a1[i];
        T elt2 = a2[i];
        // Make null compare smaller than anything else
        if ((elt1 == null) && (elt2 == null)) {
          continue;
        }
        if (elt1 == null) {
          return -1;
        }
        if (elt2 == null) {
          return 1;
        }
        int tmp = elt1.compareTo(elt2);
        if (tmp != 0) {
          return tmp;
        }
        // Check the assumption that the two elements are equal.
        assert elt1.equals(elt2);
      }
      return 0;
    }
  }

  /** Sorts arbitrary objects; used to determine equal. */
  private static final UtilPlume.ObjectComparator objectComparator =
      new UtilPlume.ObjectComparator();

  /**
   * Compare two arrays first by length (a shorter array is considered less), and if of equal length
   * compare lexically (element-by-element).
   *
   * <p>Note: this comparator imposes orderings that are inconsistent with {@link Object#equals}.
   * That is, it may return 0 if the arrays contain identical objects but are not equal according to
   * {@code equals()}.
   *
   * <p>Note: if toString returns a nondeterministic value, such as one that depends on the result
   * of {@code hashCode()}, then this comparator may yield different orderings from run to run of a
   * program.
   */
  public static final class ObjectArrayComparatorLengthFirst
      implements Comparator<Object[]>, Serializable {
    static final long serialVersionUID = 20150812L;

    /**
     * Compare two arrays by length, then lexically (element-by-element).
     *
     * @param a1 first array to compare
     * @param a2 second array to compare
     * @return a negative integer, zero, or a positive integer, depending on whether the first
     *     argument is less than, equal to, or greater than the second argument
     */
    @Pure
    // The signature on this method is unnecessarily strict because it
    // requires that the component types be identical.  The signature should
    // be compare(@PolyNull(1) Object[], @PolyNull(2) Object[]), but the
    // @PolyNull qualifier does not yet take an argument.
    @Override
    public @NonDet int compare(@PolyNull Object[] a1, @PolyNull Object[] a2) {
      if (a1 == a2) {
        return 0;
      }
      if (a1.length != a2.length) {
        return a1.length - a2.length;
      }
      for (int i = 0; i < a1.length; i++) {
        int tmp = objectComparator.compare(a1[i], a2[i]);
        if (tmp != 0) {
          return tmp;
        }
      }
      return 0;
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  /// nullness
  ///

  /**
   * Return true if a contains null.
   *
   * @param a an array
   * @return true iff some element of a is null (false if a is zero-sized)
   */
  @Pure
  public static boolean anyNull(@PolyNull Object[] a) {
    if (a.length == 0) {
      return false;
    }
    // The cast ensures that the right version of IndexOfEq gets called.
    return indexOfEq(a, (@Nullable Object) null) >= 0;
  }

  /**
   * Return true if all elements of a are null.
   *
   * @param a an array
   * @return true iff all elements of a are null (unspecified result if a is zero-sized)
   */
  @Pure
  public static boolean allNull(@PolyNull Object[] a) {
    for (int i = 0; i < a.length; i++) {
      if (!(a[i] == null)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return true if a contains null.
   *
   * @param a an array
   * @return true iff some element of a is null (false if a is zero-sized)
   */
  @Pure
  public static boolean anyNull(List<? extends @Det Object> a) {
    if (a.size() == 0) {
      return false;
    }
    // The cast ensures that the right version of IndexOfEq gets called.
    return indexOfEq(a, (@Nullable Object) null) >= 0;
  }

  /**
   * Return true if all elements of a are null.
   *
   * @param a an array
   * @return true iff all elements of a are null (unspecified result if a is zero-sized)
   */
  @Pure
  public static boolean allNull(List<?> a) {
    for (int i = 0; i < a.size(); i++) {
      if (!(a.get(i) == null)) {
        return false;
      }
    }
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////
  /// Partitioning
  ///

  /**
   * Partition a set of non-null elements into exactly k subsets. A partitioning is of type {@code
   * List<List<T>>}, where the union of the inner lists is {@code elts}. This method returns a list
   * of such partitionings.
   *
   * @param <T> type of items to be partitioned
   * @param elts items to be partitioned
   * @param k number of subsets into which to partition {@code elts}
   * @return a list of partitionings, where each contains exactly k subsets
   */
  public static <T extends @NonNull Object>
      @PolyDet List<@PolyDet("use") Partitioning<@PolyDet("use") T>> partitionInto(
          @PolyDet("up") Collection<@PolyDet("use") T> elts, @NonNegative int k) {
    return partitionInto(new @PolyDet("up") ArrayDeque<@PolyDet("use") T>(elts), k);
  }

  /**
   * Partition a set of elements into exactly k subsets. A partitioning is of type {@code
   * List<List<T>>}, where the union of the inner lists is {@code elts}. This method returns a list
   * of such partitionings.
   *
   * @param <T> type of items to be partitioned
   * @param elts items to be partitioned
   * @param k number of subsets into which to partition {@code elts}
   * @return a list of partitionings, where each contains exactly k subsets
   */
  public static <T> @PolyDet List<@PolyDet("use") Partitioning<@PolyDet("use") T>> partitionInto(
      @PolyDet("up") Queue<@PolyDet("use") T> elts, @PolyDet @NonNegative int k) {
    if (elts.size() < k) {
      throw new IllegalArgumentException();
    }
    return partitionIntoHelper(elts, Arrays.asList(new @PolyDet("up") Partitioning<T>()), k, 0);
  }

  /**
   * Returns a set of partitionings, each of size numEmptyParts + numNonemptyParts. A helper method
   * for {@link #partitionInto}.
   *
   * @param <T> type of items to be partitioned
   * @param elts the elements that remain to be added to the partitionings
   * @param resultSoFar a list of partitionings, each of which has numNonemptyParts parts
   * @param numEmptyParts the number of partitions in the partitioning that are empty so far
   * @param numNonemptyParts the number of partitions in the partitioning that have at least one
   *     member so far
   * @return a list of partitionings, where each contains exactly k subsets
   */
  // "p.addToPart(i, ...)" is OK: i is < numNonemptyParts
  //  and p.size() = numNonemptyParts + numEmptyParts, both of which are non-negative.
  @SuppressWarnings({
    "determinism:argument.type.incompatible", // Iteration over OrderNonDet collection for assigning into another
    "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for assigning into another
  })
  public static <T>
      @PolyDet("up") List<@PolyDet("use") Partitioning<@PolyDet("use") T>> partitionIntoHelper(
          @PolyDet("up") Queue<@PolyDet("use") T> elts,
              @PolyDet("up") List</*@ LengthIs("#3")*/ @PolyDet("use") Partitioning<@PolyDet("use") T>>
                  resultSoFar,
          @PolyDet @NonNegative int numEmptyParts,
          @PolyDet @NonNegative int numNonemptyParts) {

    if (numEmptyParts > elts.size()) {
      throw new IllegalArgumentException(numEmptyParts + " > " + elts.size());
    }

    if (elts.isEmpty()) {
      return resultSoFar;
    }

    @PolyDet("up") Queue<@PolyDet("use") T> eltsRemaining = new @PolyDet("up") ArrayDeque<@PolyDet("use") T>(elts);
    T elt = eltsRemaining.remove();

    @PolyDet("up") List<@PolyDet("use") Partitioning<@PolyDet("use") T>> result =
        new @PolyDet("up") ArrayList<@PolyDet("use") Partitioning<@PolyDet("use") T>>();

    // Put elt in an existing part in the partitioning.
    if (elts.size() > numEmptyParts) {
      @PolyDet("up") List<@PolyDet("use") Partitioning<@PolyDet("use") T>> resultSoFar_augmented =
          new @PolyDet("up") ArrayList<@PolyDet("use") Partitioning<@PolyDet("use") T>>();
      for (int i = 0; i < numNonemptyParts; i++) {
        for (Partitioning<T> p : resultSoFar) {
          resultSoFar_augmented.add(p.addToPart(i, elt));
        }
      }
      result.addAll(
          partitionIntoHelper(
              eltsRemaining, resultSoFar_augmented, numEmptyParts, numNonemptyParts));
    }

    // Put elt in a newly-created part in the partitioning.
    if (numEmptyParts > 0) {
      List<Partitioning<T>> resultSoFar_augmented = new ArrayList<Partitioning<T>>();
      for (Partitioning<T> p : resultSoFar) {
        resultSoFar_augmented.add(p.addToPart(numNonemptyParts, elt));
      }
      result.addAll(
          partitionIntoHelper(
              eltsRemaining, resultSoFar_augmented, numEmptyParts - 1, numNonemptyParts + 1));
    }

    return result;
  }

  /** A partitioning is a set of sets. It adds a few methods to {@code ArrayList<ArrayList<T>>}. */
  static class Partitioning<T extends @NonNull Object> extends ArrayList<ArrayList<T>> {

    static final long serialVersionUID = 20170418;

    /** Empty constructor. */
    Partitioning() {}

    /**
     * Copy constructor.
     *
     * @param other the Partitioning to make a copy of
     */
    Partitioning(Partitioning<T> other) {
      super(other);
    }

    /**
     * The set that has been partitioned. That is, all the elements that have been added to this.
     * Equivalently, the union of all the partitions.
     *
     * @return all the elements in any part of the Partitioning
     */
    ArrayList<T> partitionedSet() {
      @PolyDet ArrayList<T> result = new @PolyDet ArrayList<>();
      for (List<T> part : this) {
        result.addAll(part);
      }
      return result;
    }

    /**
     * True if this is a partitioning for {@code elts}.
     *
     * @param elts the elements that might be partitioned by this
     * @return true if this is a partitioning for {@code elts}
     */
    boolean isPartitioningFor(List<T> elts) {
      // Inefficient O(n^2) implementation.  We can do O(n log n) if desired.
      @PolyDet ArrayList<T> ps = partitionedSet();
      return ps.size() == elts.size() && ps.containsAll(elts);
    }

    /**
     * Returns a new partitioning just like this one, but with elt added to the ith part.
     *
     * @param i the index of an existing part, or the current size (to create a new part)
     * @param elt the element to add
     * @return a new partitioning just like this one, but with elt added to the ith part
     */
    @SuppressWarnings({
      "determinism:cast.unsafe.constructor.invocation", // Iteration over OrderNonDet collection for adding an element
      "determinism:method.invocation.invalid" // Iteration over OrderNonDet collection for adding an element
    })
    @PolyDet Partitioning<T> addToPart(@NonNegative int i, @PolyDet("use") T elt) {
      @PolyDet Partitioning<T> result = new Partitioning<>(this);
      if (size() == i) {
        @PolyDet ArrayList<T> newPart = newArrayList(elt);
        result.add(newPart);
      } else {
        @PolyDet ArrayList<T> newPart = new @PolyDet ArrayList<>(result.get(i));
        newPart.add(elt);
        result.set(i, newPart);
      }
      return result;
    }
  }

  /**
   * Return a singleton ArrayList containing the given element.
   *
   * @param <T> the element type of the list
   * @param elt the element to put in the ArrayList
   * @return a singleton ArrayList containing {@code elt}
   */
  private static <T> ArrayList<T> newArrayList(@PolyDet T elt) {
    @PolyDet ArrayList<T> result = new @PolyDet ArrayList<>(1);
    result.add(elt);
    return result;
  }

  // /**
  //  * Return a singleton ArrayDeque containing the given element.
  //  *
  //  * @param <T> the element type of the list
  //  * @param elt the element to put in the ArrayDeque
  //  * @return a singleton ArrayDeque containing {@code elt}
  //  */
  // private static <T extends @NonNull Object> ArrayDeque<T> newArrayDeque(T elt) {
  //   ArrayDeque<T> result = new ArrayDeque<>();
  //   result.add(elt);
  //   return result;
  // }
}
