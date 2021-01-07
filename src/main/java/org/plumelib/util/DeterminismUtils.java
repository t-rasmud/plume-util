package org.plumelib.util;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import org.checkerframework.checker.determinism.qual.*;
import org.checkerframework.common.value.qual.ArrayLen;

@SuppressWarnings("determinism")
public class DeterminismUtils {

  ///
  /// Reduce (aggregation)
  ///

  /**
   * Applies a commutative aggregation operation to the given array.
   *
   * @param <T> the type of the arguments and return of the function
   * @param a the array to aggregate
   * @param f the aggregation function
   * @return the aggregation of {@code a}, with respect to {@code f}
   */
  public static <T> @PolyDet("down") T reduce(@PolyDet T[] a, BinaryOperator<T> f) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to reduce");
    }
    return Arrays.stream(a).reduce(f).get();
  }

  /**
   * Applies a commutative aggregation operation to the given array.
   *
   * @param a the array to aggregate
   * @param f the aggregation function
   * @return the aggregation of {@code a}, with respect to {@code f}
   */
  public static @PolyDet("down") int reduce(int[] a, IntBinaryOperator f) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to reduce");
    }
    return Arrays.stream(a).reduce(f).getAsInt();
  }

  /**
   * Applies a commutative aggregation operation to the given array.
   *
   * @param a the array to aggregate
   * @param f the aggregation function
   * @return the aggregation of {@code a}, with respect to {@code f}
   */
  public static @PolyDet("down") long reduce(long[] a, LongBinaryOperator f) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to reduce");
    }
    return Arrays.stream(a).reduce(f).getAsLong();
  }

  /**
   * Applies a commutative aggregation operation to the given array.
   *
   * @param a the array to aggregate
   * @param f the aggregation function
   * @return the aggregation of {@code a}, with respect to {@code f}
   */
  public static @PolyDet("down") double reduce(double[] a, DoubleBinaryOperator f) {
    if (a.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Empty array passed to reduce");
    }
    return Arrays.stream(a).reduce(f).getAsDouble();
  }
}
