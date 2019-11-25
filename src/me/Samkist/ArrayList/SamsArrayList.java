/* Created By: Sam Pizette
 * On Date: 11/22/2019
 * Project Name: ArrayList
 */
package me.Samkist.ArrayList;

import java.util.*;

@SuppressWarnings("unused")
public class SamsArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable {
    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Object[] elementData;

    public SamsArrayList() {
        elementData = new Object[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    public SamsArrayList(Object[] data) {
        elementData = data;
        size = data.length;
    }

    public boolean add(E e) {
        if(size == elementData.length) {
            expandArray();
        }
        elementData[size++] = e;
        return true;
    }

    public void add(int index, E element) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if(size() +1 == elementData.length)
            expandArray();
        Object[] data = elementData;
        Object[] cache = new Object[size() - index];
        int x = 0;
        for(int i = index; i < size; i++) {
            cache[x++] = data[i];
        }
        data[index] = element;
        System.out.println("After index is replaced");
        for(Object o : data)
            System.out.println(o);
        System.out.println(size());
        x = 0;
        for(int i = index+1; i < size()+1; i++) {
            data[i] = cache[x++];
        }
        System.out.println("After cache is added on");
        for(Object o : data)
            System.out.println(o);
        clear();
        for(Object o : data)
            add((E) o);
    }

    public boolean addAll(Collection<? extends E> c) {
        c.forEach(this::add);
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends E> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        Object[] cArray = c.toArray();
        int sizeAddition = cArray.length;
        int objectsMoved = size - index;
        if(objectsMoved > 0)
            System.arraycopy(elementData, index, elementData, index + sizeAddition, objectsMoved);
        System.arraycopy(cArray, 0, elementData, index, sizeAddition);
        size += sizeAddition;
        return true;
    }

    public void clear() {
        elementData = new Object[]{};
    }

    public Object clone() {
        try {
            SamsArrayList<?> clone = (SamsArrayList<?>)super.clone();
            clone.elementData = Arrays.copyOf(elementData, size);
            return clone;
        } catch(CloneNotSupportedException ignored) {
            throw new InternalError(ignored);
            //Internal error is the only way to not return anything after try catch oof
        }
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is nothing at index " + index + " array size is " + size);
        }
        return (E) elementData[index];
    }
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is nothing at index " + index + " array size is " + size);
        }
        Object removedElement = elementData[index];
        if (size - 1 - index >= 0) System.arraycopy(elementData, index + 1, elementData, index, size - 1 - index);
        size--;
        return (E) removedElement;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    private void expandArray() {
        int newSize = elementData.length * 2;
        elementData = Arrays.copyOf(elementData, newSize);
    }

    public void ensureCapacity(int minCapacity) {
        elementData = Arrays.copyOf(elementData, minCapacity);
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> returnedList = null;
        for(int i = fromIndex; i < toIndex; i++) {
            returnedList.add((E) elementData[i]);
        }
        return returnedList;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if(a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if(a.length > size)
            a[size] = null;
        return a;
    }

    public void trimToSize() {
        elementData = Arrays.copyOf(elementData, size());
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int lastIndexOf(Object o) {
        int v = -1;
        int s = size;
        for(int i = 0; i < s; i++)
            if(elementData[i].equals(o))v=i;
        return v;
    }

    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for(int i = 0; i < size(); i++) {
            if(c.contains(elementData[i])) {
                fastRemove(i--);
                b = true;
            }
        }
        return b;
    }

    public boolean retainAll(Collection<?> c) {
        boolean b = false;
        for(int i = 0; i < size(); i++) {
            if(!c.contains(elementData[i])) {
                fastRemove(i--);
                b = true;
            }
        }
        return b;
    }

    public E set(int index, E element)  {
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        elementData[index] = element;
        return element;
    }



}