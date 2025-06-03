package pascal.taie.android.info;

import pascal.taie.util.collection.Sets;

import java.util.Set;

public record TransferFilterInfo(Set<String> classNames,
                                 Set<String> actions,
                                 Set<String> categories,
                                 Set<UriData> data) {

    public boolean emptyImplicitFilterInfo() {
        return actions.isEmpty() && categories.isEmpty() && data.isEmpty();
    }

    private static TransferFilterInfo of(Builder builder) {
        return new TransferFilterInfo(builder.classNames, builder.actions, builder.categories, builder.data);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Set<String> classNames = Sets.newSet();

        private Set<String> actions = Sets.newSet();

        private Set<String> categories = Sets.newSet();

        private Set<UriData> data = Sets.newSet();

        private Builder() {
        }

        public Builder classNames(Set<String> classNames) {
            this.classNames = classNames;
            return this;
        }

        public Builder actions(Set<String> actions) {
            this.actions.addAll(actions);
            return this;
        }

        public Builder categories(Set<String> categories) {
            this.categories.addAll(categories);
            return this;
        }

        public Builder data(Set<UriData> data) {
            this.data.addAll(data);
            return this;
        }
        public TransferFilterInfo build() {
            return TransferFilterInfo.of(this);
        }

    }
}
