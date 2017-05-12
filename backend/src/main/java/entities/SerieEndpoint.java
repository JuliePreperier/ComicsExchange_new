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
        name = "serieApi",
        version = "v1",
        resource = "serie",
        namespace = @ApiNamespace(
                ownerDomain = "entities",
                ownerName = "entities",
                packagePath = ""
        )
)
public class SerieEndpoint {

    private static final Logger logger = Logger.getLogger(SerieEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Serie.class);
    }

    /**
     * Returns the {@link Serie} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Serie} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "serie/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Serie get(@Named("id") int id) throws NotFoundException {
        logger.info("Getting Serie with ID: " + id);
        Serie serie = ofy().load().type(Serie.class).id(id).now();
        if (serie == null) {
            throw new NotFoundException("Could not find Serie with ID: " + id);
        }
        return serie;
    }

    /**
     * Inserts a new {@code Serie}.
     */
    @ApiMethod(
            name = "insert",
            path = "serie",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Serie insert(Serie serie) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that serie.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(serie).now();
        logger.info("Created Serie with ID: " + serie.getId());

        return ofy().load().entity(serie).now();
    }

    /**
     * Updates an existing {@code Serie}.
     *
     * @param id    the ID of the entity to be updated
     * @param serie the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Serie}
     */
    @ApiMethod(
            name = "update",
            path = "serie/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Serie update(@Named("id") int id, Serie serie) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(serie).now();
        logger.info("Updated Serie: " + serie);
        return ofy().load().entity(serie).now();
    }

    /**
     * Deletes the specified {@code Serie}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Serie}
     */
    @ApiMethod(
            name = "remove",
            path = "serie/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") int id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Serie.class).id(id).now();
        logger.info("Deleted Serie with ID: " + id);
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
            path = "serie",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Serie> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Serie> query = ofy().load().type(Serie.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Serie> queryIterator = query.iterator();
        List<Serie> serieList = new ArrayList<Serie>(limit);
        while (queryIterator.hasNext()) {
            serieList.add(queryIterator.next());
        }
        return CollectionResponse.<Serie>builder().setItems(serieList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int id) throws NotFoundException {
        try {
            ofy().load().type(Serie.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Serie with ID: " + id);
        }
    }
}