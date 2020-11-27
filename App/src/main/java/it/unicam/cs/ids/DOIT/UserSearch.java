package it.unicam.cs.ids.DOIT;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserSearch {

    private static UserSearch userSearch;

    private UserSearch() {
    }

    public static UserSearch getIstance() {
        if(userSearch==null)
            userSearch = new UserSearch();
        return userSearch;
    }

    public List<User> SearchUser(Predicate<User> predicateRole, Category category) {
        return DB.TOTAL_USERS.stream()
                .filter(u -> u.getCategory() == category)
                .filter(predicateRole)
                .collect(Collectors.toList());
    }
}
