package com.yesipov.api;

import com.google.gson.Gson;
import com.yesipov.dao.UserDao;
import com.yesipov.model.User;

import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class UserApi {
    UserDao userDao = new UserDao();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addUser(@FormParam("name") String name,
                            @FormParam("age") int age) {
        User user = new User(name, age);
        return Response
                .status(Response.Status.OK)
                .entity("User created \n" + userDao.addUser(user).toString())
                .build();
    }

    @Path("/getUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response
                .status(Response.Status.OK)
                .entity(userDao.getAllUsers().toString())
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(userDao.getUserById(id).toString())
                    .build();
        } catch (NoResultException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage() + "\n USER NOT FOUND")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateUser(@FormParam("id") long id,
                               @FormParam("name") String name,
                               @FormParam("age") int age) {
        Gson gson = new Gson();
        try {
            String json = userDao.getUserById(id).toString();
            User user = gson.fromJson(json, User.class);
            user.setName(name);
            user.setAge(age);
            userDao.updateUser(user);

            return Response
                    .status(Response.Status.OK)
                    .entity(userDao.getUserById(id).toString())
                    .build();
        } catch (NoResultException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("No users with id = " + id)
                    .build();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteUserById(@PathParam("id") long id) {
        try {
            String json = userDao.getUserById(id).toString();
            return Response
                    .status(Response.Status.OK)
                    .entity(userDao.deleteUserById(id).toString())
                    .build();
        } catch (NoResultException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("No users with id = " + id)
                    .build();
        }
    }
}
