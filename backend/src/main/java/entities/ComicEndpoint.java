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
        name = "comicApi",
        version = "v1",
        resource = "comic",
        namespace = @ApiNamespace(
                ownerDomain = "entities",
                ownerName = "entities",
                packagePath = ""
        )
)
public class ComicEndpoint {

    private static final Logger logger = Logger.getLogger(ComicEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Comic.class);
    }

    /**
     * Returns the {@link Comic} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Comic} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "comic/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Comic get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Comic with ID: " + id);
        Comic comic = ofy().load().type(Comic.class).id(id).now();
        if (comic == null) {
            throw new NotFoundException("Could not find Comic with ID: " + id);
        }
        return comic;
    }

    /**
     * Inserts a new {@code Comic}.
     */
    @ApiMethod(
            name = "insert",
            path = "comic",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Comic insert(Comic comic) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that comic.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(comic).now();
        logger.info("Created Comic with ID: " + comic.getId());

        return ofy().load().entity(comic).now();
    }

    /**
     * Updates an existing {@code Comic}.
     *
     * @param id    the ID of the entity to be updated
     * @param comic the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Comic}
     */
    @ApiMethod(
            name = "update",
            path = "comic/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Comic update(@Named("id") Long id, Comic comic) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(comic).now();
        logger.info("Updated Comic: " + comic);
        return ofy().load().entity(comic).now();
    }

    /**
     * Deletes the specified {@code Comic}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Comic}
     */
    @ApiMethod(
            name = "remove",
            path = "comic/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Comic.class).id(id).now();
        logger.info("Deleted Comic with ID: " + id);
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
            path = "comic",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Comic> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Comic> query = ofy().load().type(Comic.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Comic> queryIterator = query.iterator();
        List<Comic> comicList = new ArrayList<Comic>(limit);
        while (queryIterator.hasNext()) {
            comicList.add(queryIterator.next());
        }
        return CollectionResponse.<Comic>builder().setItems(comicList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Comic.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Comic with ID: " + id);
        }
    }
}