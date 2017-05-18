package entities;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "ownerBooksApi",
        version = "v1",
        resource = "ownerBooks",
        namespace = @ApiNamespace(
                ownerDomain = "entities",
                ownerName = "entities",
                packagePath = ""
        )
)
public class OwnerBooksEndpoint {

    private static final Logger logger = Logger.getLogger(OwnerBooksEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(OwnerBooks.class);
    }

    /**
     * Returns the {@link OwnerBooks} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code OwnerBooks} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "ownerBooks/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OwnerBooks get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting OwnerBooks with ID: " + id);
        OwnerBooks ownerBooks = ofy().load().type(OwnerBooks.class).id(id).now();
        if (ownerBooks == null) {
            throw new NotFoundException("Could not find OwnerBooks with ID: " + id);
        }
        return ownerBooks;
    }

    /**
     * Inserts a new {@code OwnerBooks}.
     */
    @ApiMethod(
            name = "insert",
            path = "ownerBooks",
            httpMethod = ApiMethod.HttpMethod.POST)
    public OwnerBooks insert(OwnerBooks ownerBooks) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that ownerBooks.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(ownerBooks).now();
        logger.info("Created OwnerBooks with ID: " + ownerBooks.getId());

        return ofy().load().entity(ownerBooks).now();
    }

    /**
     * Updates an existing {@code OwnerBooks}.
     *
     * @param id         the ID of the entity to be updated
     * @param ownerBooks the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code OwnerBooks}
     */
    @ApiMethod(
            name = "update",
            path = "ownerBooks/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public OwnerBooks update(@Named("id") Long id, OwnerBooks ownerBooks) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(ownerBooks).now();
        logger.info("Updated OwnerBooks: " + ownerBooks);
        return ofy().load().entity(ownerBooks).now();
    }

    /**
     * Deletes the specified {@code OwnerBooks}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code OwnerBooks}
     */
    @ApiMethod(
            name = "remove",
            path = "ownerBooks/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(OwnerBooks.class).id(id).now();
        logger.info("Deleted OwnerBooks with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "ownerBooks",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<OwnerBooks> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<OwnerBooks> query = ofy().load().type(OwnerBooks.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<OwnerBooks> queryIterator = query.iterator();
        List<OwnerBooks> ownerBooksList = new ArrayList<OwnerBooks>(limit);
        while (queryIterator.hasNext()) {
            ownerBooksList.add(queryIterator.next());
        }
        return CollectionResponse.<OwnerBooks>builder().setItems(ownerBooksList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(OwnerBooks.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find OwnerBooks with ID: " + id);
        }
    }
}