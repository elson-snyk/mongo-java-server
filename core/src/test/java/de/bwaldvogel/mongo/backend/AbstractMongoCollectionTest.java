package de.bwaldvogel.mongo.backend;

import static de.bwaldvogel.mongo.TestUtils.json;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.bwaldvogel.mongo.bson.Document;
import de.bwaldvogel.mongo.bson.ObjectId;

public class AbstractMongoCollectionTest {

    private static class TestCollection extends AbstractMongoCollection<Object> {

        TestCollection(String databaseName, String collectionName, String idField) {
            super(databaseName, collectionName, idField);
        }

        @Override
        protected Object addDocumentInternal(Document document) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int count() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Document getDocument(Object position) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void removeDocument(Object position) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Object findDocumentPosition(Document document) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Iterable<Document> matchDocuments(Document query, Iterable<Object> positions, Document orderBy,
                                                    int numberToSkip, int numberToReturn) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Iterable<Document> matchDocuments(Document query, Document orderBy, int numberToSkip,
                                                    int numberToReturn) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void updateDataSize(long sizeDelta) {
        }

        @Override
        protected long getDataSize() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void handleUpdate(Document document) {
            // noop
        }
    }

    private TestCollection collection;

    @Before
    public void setUp() {
        this.collection = new TestCollection("some database", "some collection", "_id");
    }

    @Test
    public void testConvertSelector() throws Exception {
        assertThat(collection.convertSelectorToDocument(json("")))
            .isEqualTo(json(""));

        assertThat(collection.convertSelectorToDocument(json("_id: 1")))
            .isEqualTo(json("_id: 1"));

        assertThat(collection.convertSelectorToDocument(json("_id: 1, $set: {foo: 'bar'}")))
            .isEqualTo(json("_id: 1"));

        assertThat(collection.convertSelectorToDocument(json("_id: 1, 'e.i': 14")))
            .isEqualTo(json("_id: 1, e: {i: 14}"));

        assertThat(collection.convertSelectorToDocument(json("_id: 1, 'e.i.y': {foo: 'bar'}")))
            .isEqualTo(json("_id: 1, e: {i: {y: {foo: 'bar'}}}"));
    }

    @Test
    public void testDeriveDocumentId() throws Exception {
        assertThat(collection.deriveDocumentId(json(""))).isInstanceOf(ObjectId.class);
        assertThat(collection.deriveDocumentId(json("a: 1"))).isInstanceOf(ObjectId.class);
        assertThat(collection.deriveDocumentId(json("_id: 1"))).isEqualTo(1);
        assertThat(collection.deriveDocumentId(json("_id: {$in: [1]}"))).isEqualTo(1);
        assertThat(collection.deriveDocumentId(json("_id: {$in: []}"))).isInstanceOf(ObjectId.class);
    }
}
