package response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ToDo {
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String description;
}
