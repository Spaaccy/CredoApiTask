package ge.credo.testautomation.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResult {
    private Integer id;
    private String testName;
    private String status;
    private String executionTime;
}

