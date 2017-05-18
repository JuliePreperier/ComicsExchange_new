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
        name = "authorsApi",
        version = "v1",
        resource = "authors",
        namespace = @ApiNamespace(
                ownerDomain = "entities",
                ownerName = "entities",
                packagePath = ""
        )
)
public class AuthorsEndpoint {

    private static final Logger logger = Logger.getLogger(AuthorsEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Authors.class);
    }

    /**
     * Returns the {@link Authors} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Authors} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "authors/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Authors get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Authors with ID: " + id);
        Authors authors = ofy().load().type(Authors.class).id(id).now();
        if (authors == null) {
            throw new NotFoundException("Could not find Authors with ID: " + id);
        }
        return authors;
    }

    /**
     * Inserts a new {@code Authors}.
     */
    @ApiMethod(
            name = "insert",
            path = "authors",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Authors insert(Authors authors) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that authors.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(authors).now();
        logger.info("Created Authors with ID: " + authors.getId());

        return ofy().load().entity(authors).now();
    }

    /**
     * Updates an existing {@code Authors}.
     *
     * @param id      the ID of the entity to be updated
     * @param authors the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Authors}
     */
    @ApiMethod(
            name = "update",
            path = "authors/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Authors update(@Named("id") Long id, Authors authors) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(authors).now();
        logger.info("Updated Authors: " + authors);
        return ofy().load().entity(authors).now();
    }

    /**
     * Deletes the specified {@code Authors}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Authors}
     */
    @ApiMethod(
            name = "remove",
            path = "authors/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Authors.class).id(id).now();
        logger.info("Deleted Authors with ID: " + id);
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
            path = "authors",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Authors> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Authors> query = ofy().load().type(Authors.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Authors> queryIterator = query.iterator();
        List<Authors> authorsList = new ArrayList<Authors>(limit);
        while (queryIterator.hasNext()) {
            authorsList.add(queryIterator.next());
        }
        return CollectionResponse.<Authors>builder().setItems(authorsList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Authors.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Authors with ID: " + id);
        }
    }
}