package Memento.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeedCategory {
    private String name;
    private List<String> tags;
}

