package cn.unis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

/**
 *
 * @author fanzz
 *
 * @param <T>
 */
public class ListUtils<T> {
	/**
	 * 集合差集
	 *
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> diff(List<T> ls1, List<T> ls2) {
		List<T>  list = (List<T>) new ArrayList<Object>(Arrays.asList(new Object[ls1.size()]));
		Collections.copy(list, ls1);
		list.removeAll(ls2);
		return list;
	}
	/**
	 * 集合并集
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> union(List<T> ls1, List<T> ls2) {
		List<T>  list = (List<T>) new ArrayList<Object>(Arrays.asList(new Object[ls1.size()]));
		Collections.copy(list, ls1);
		list.addAll(ls2);
		return list;
	}

	/**
	 * 集合并集（去除相同）
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	public List<T> unionWithOutSame(List<T> ls1, List<T> ls2){
		Set<T> set1 = new HashSet<T>(ls1);
		Set<T> set2 = new HashSet<T>(ls2);
		set1.addAll(set2);
		List<T> list = new ArrayList<T>(set1);
		return list;
	}
	/**
	 * 集合交集
	 * @param ls
	 * @param ls2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T>  intersect(List<T>  ls, List<T>  ls2) {
		List<T>  list = (List<T>) new ArrayList<Object>(Arrays.asList(new Object[ls.size()]));
		Collections.copy(list, ls);
		list.retainAll(ls2);
		return list;
	}

	public static void main(String[] args){
		List<Integer> list1 = Lists.newArrayList();
		List<Integer> list2 = Lists.newArrayList();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);
		list2.add(2);
		list2.add(3);
		list2.add(4);
		list2.add(5);
		list2.add(6);
		ListUtils<Integer> listUtils = new ListUtils<Integer>();
		System.out.println(listUtils.diff(list1, list2));
		System.out.println(listUtils.union(list1, list2));
		System.out.println(listUtils.unionWithOutSame(list1, list2));
		System.out.println(listUtils.intersect(list1, list2));
	}
}
