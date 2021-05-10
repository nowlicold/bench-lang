package com.bench.lang.base.math.permutation;

/**
 * 组合算法
 * 
 * int[] indices; <br>
 * String[] elements = {"a", "b", "c", "d"};<br>
 * PermutationGenerator x = new PermutationGenerator (elements.length);<br>
 * StringBuffer permutation;<br>
 * while (x.hasMore ()) {<br>
 * ----permutation = new StringBuffer ();<br>
 * ----indices = x.getNext ();<br>
 * ----for (int i = 0; i < indices.length; i++) {<br>
 * ------- permutation.append (elements[indices[i]]);<br>
 * }<br>
 * System.out.println (permutation.toString ());<br>
 * }
 * 
 * @author cold
 * 
 * @version $Id: PermutationGenerator.java, v 0.1 2011-6-13 下午12:32:23 cold
 *          Exp $
 */
public class PermutationGenerator {

	private int n, r;
	private int[] index;
	private boolean hasNext = true;

	public PermutationGenerator(int n, int r) {
		this.n = n;
		this.r = r;
		index = new int[n];
		for (int i = 0; i < n; i++)
			index[i] = i;
		reverseAfter(r - 1);
	}

	public boolean hasMore() {
		return hasNext;
	}

	// Based on code from KodersCode:
	// The algorithm is from Applied Combinatorics, by Alan Tucker.
	// Move the index forward a notch. The algorithm first finds the
	// rightmost index that is less than its neighbor to the right. This
	// is the dip point. The algorithm next finds the least element to
	// the right of the dip that is greater than the dip. That element is
	// switched with the dip. Finally, the list of elements to the right
	// of the dip is reversed.
	//
	// For example, in a permutation of 5 items, the index may be
	// {1, 2, 4, 3, 0}. The dip is 2 the rightmost element less
	// than its neighbor on its right. The least element to the right of
	// 2 that is greater than 2 is 3. These elements are swapped,
	// yielding {1, 3, 4, 2, 0}, and the list right of the dip point is
	// reversed, yielding {1, 3, 0, 2, 4}.

	private void moveIndex() {
		// find the index of the first element that dips
		int i = rightmostDip();
		if (i < 0) {
			hasNext = false;
			return;
		}

		// find the smallest element to the right of the dip
		int smallestToRightIndex = i + 1;
		for (int j = i + 2; j < n; j++)
			if ((index[j] < index[smallestToRightIndex]) && (index[j] > index[i]))
				smallestToRightIndex = j;

		// switch dip element with smallest element to its right
		swap(index, i, smallestToRightIndex);

		if (r - 1 > i) {
			// reverse the elements to the right of the dip
			reverseAfter(i);
			// reverse the elements to the right of r - 1
			reverseAfter(r - 1);
		}
	}

	public int[] getNext() {
		if (!hasNext)
			return null;
		int[] result = new int[r];
		for (int i = 0; i < r; i++)
			result[i] = index[i];
		moveIndex();
		return result;
	}

	// Reverse the index elements to the right of the specified index.
	private void reverseAfter(int i) {
		int start = i + 1;
		int end = n - 1;
		while (start < end) {
			swap(index, start, end);
			start++;
			end--;
		}
	}

	// return int the index of the first element from the right
	// that is less than its neighbor on the right.
	private int rightmostDip() {
		for (int i = n - 2; i >= 0; i--)
			if (index[i] < index[i + 1])
				return i;
		return -1;
	}

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		PermutationGenerator g = new PermutationGenerator(5, 2);
		while (g.hasMore()) {
			int[] ret = g.getNext();
			String str = "";
			for (int i : ret) {
				str += i;
			}
			System.out.println(str);
		}
	}
}
