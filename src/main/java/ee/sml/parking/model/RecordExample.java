package ee.sml.parking.model;

public record RecordExample(
        String name,
        String lastName
) {

    RecordExample ok() {
        return new RecordExample("name", "lastname");
    }
}
