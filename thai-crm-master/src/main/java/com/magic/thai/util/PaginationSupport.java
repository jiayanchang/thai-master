package com.magic.thai.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationSupport {
    private static int DEFAULT_COUNT_ON_EACH_PAGE = 10;

    private List<?> items;

    private int totalCount; 

    // private int startIndex;
    private int countOnEachPage = 20;

    private int currentPage = 1; 

    public PaginationSupport() {
        this(DEFAULT_COUNT_ON_EACH_PAGE);
        items=new ArrayList();      
    }

    public PaginationSupport(int countOnEachPage) {
        this.currentPage = 1;
        if (countOnEachPage < 1) {
            throw new IllegalArgumentException("Count should be greater than zero!");
        } else {
            this.countOnEachPage = countOnEachPage;
        }
    }

    public PaginationSupport(int currentPage, int countOnEachPage) {
        if (currentPage < 1) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        if (countOnEachPage < 1) {
            throw new IllegalArgumentException("Count should be greater than zero!");
        } else {
            this.countOnEachPage = countOnEachPage;
        }
    }

    /*
     * public PaginationSupport(List items, int startIndex, int totalCount, int
     * countOnEachPage) { this.items = items; this.startIndex = startIndex;
     * this.totalCount = totalCount; this.countOnEachPage = countOnEachPage; }
     */

    /*
     * public void setStartIndex(int startIndex) { this.startIndex = startIndex; }
     */

    public void setCountOnEachPage(int countOnEachPage) {
        this.countOnEachPage = countOnEachPage;
    }

    public List<?> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getEndIndex() {
        int endIndex = getStartIndex() + countOnEachPage;
        if (endIndex > totalCount)
            return totalCount;
        else
            return endIndex;
    }

    public int getStartIndex() {

        if ((currentPage - 1) * countOnEachPage > totalCount)
            return totalCount;
        if ((currentPage - 1) * countOnEachPage < 0)
            return 0;
        else
            return (currentPage - 1) * countOnEachPage;
    }

    /*
     * public int getNextIndex() { return getNextStartIndexes()[0]; }
     * 
     * public int getPreviousIndex() { int previousIndexes[] =
     * getPreviousStartIndexes(); return previousIndexes[previousIndexes.length -
     * 1]; }
     */

    public int getNextPage() {
        int[] nexts = getNextPageIndexes();
        if (nexts != null && nexts.length > 0)
            return nexts[0];
        else
            return 0;
    }

    public int getPreviousPage() {
        int previousIndexes[] = getPreviousPageIndexes();
        if (previousIndexes != null && previousIndexes.length > 0)
            return previousIndexes[previousIndexes.length - 1];
        else
            return 0;
    }

    /*
     * public int[] getNextStartIndexes() { int index = getEndIndex(); if (index ==
     * totalCount) return null; int count = (totalCount - index) /
     * countOnEachPage; if ((totalCount - index) % countOnEachPage > 0) count++;
     * int result[] = new int[count]; for (int i = 0; i < count; i++) {
     * result[i] = index; index += countOnEachPage; }
     * 
     * return result; }
     */

    public int[] getNextPageIndexes() {
        int index = getEndIndex();
        if (index == totalCount)
            return null;
        int count = (totalCount - index) / countOnEachPage;
        if ((totalCount - index) % countOnEachPage > 0)
            count++;
        int result[] = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = i + currentPage + 1;
        }

        return result;
    }

    public int getTotalPageCount() {
        int count = totalCount / countOnEachPage;
        if (totalCount % countOnEachPage > 0)
            count++;
        return count;
    }

    /*
     * public int[] getPreviousStartIndexes() { int index = getStartIndex(); if
     * (index == 0) return null; int count = index / countOnEachPage; if (index %
     * countOnEachPage > 0) count++; int result[] = new int[count]; for (int i =
     * count - 1; i > 0; i--) { index -= countOnEachPage; result[i] = index; }
     * 
     * return result; }
     */

    public int[] getPreviousPageIndexes() {
        int index = getStartIndex();
        if (index == 0)
            return null;
        int count = index / countOnEachPage;
        if (index % countOnEachPage > 0)
            count++;
        int result[] = new int[count];
        for (int i = count; i > 0; i--) {
            // index -= countOnEachPage;
            result[i - 1] = i;
        }

        return result;
    }

    public int getCountOnEachPage() {
        return countOnEachPage;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 1) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

}