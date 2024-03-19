package fr.vitalitte.vitalittebackend.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListMapperUtil {

    /**
     * @param mapper
     * @param input
     * @param <E>
     * @param <S>
     * @return
     */
    public static <E, S> List<S> mapList(Function<E, S> mapper, List<E> input) {
        List<S> dtoList = new ArrayList<>();

        for (E item : input) {
            S dto = mapper.apply(item);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
