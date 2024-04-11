package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training implements Comparable<Training>{
    private LocalDate trainingDate = LocalDate.now();
    private DefaultUser user;
    private String trainingType = "";
    private Duration trainingDuration;
    private Integer trainingCalories;
    private Map<String, String> trainingOther = new HashMap<>();

    @Override
    public int compareTo(Training o) {
        return trainingDate.compareTo(o.trainingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(trainingDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(trainingDate, training.trainingDate) && Objects.equals(user, training.user);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тренировка{");
        sb.append("Дата тренировки : ").append(trainingDate);
        sb.append(", пользователь : ").append(user.getLogin());
        sb.append(", тип тренировки : ").append(trainingType);
        sb.append(", продолжительность : ").append(trainingDuration.toMinutes());
        sb.append(", калории : ").append(trainingCalories);
        sb.append(", другое : ").append(trainingOther);
        sb.append('}').append('\n').append('\n');;
        return sb.toString();
    }
}
