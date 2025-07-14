package com.newrise.applicanttrackingsystem.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PaginationUtil 
{
    public static <T> List<T> paginateSet(Set<T> set, int page, int size) {
        List<T> list = new ArrayList<>(set);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, list.size());

        if (fromIndex >= list.size()) 
        {
            return new ArrayList<>(); 
        }

        return list.subList(fromIndex, toIndex);
    }
}