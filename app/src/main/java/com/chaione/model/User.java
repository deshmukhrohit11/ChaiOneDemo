package com.chaione.model;

/**
 * The Class User.
 */
public class User {

    /**
     * The avatar_image.
     */
    private AvatarImage avatar_image;

    /**
     * The name.
     */
    private String name;

    /**
     * Gets the avatar_image.
     *
     * @return the avatar_image
     */
    public AvatarImage getAvatar_image() {
        return this.avatar_image;
    }

    /**
     * Sets the avatar_image.
     *
     * @param avatar_image the new avatar_image
     */
    public void setAvatar_image(AvatarImage avatar_image) {
        this.avatar_image = avatar_image;
    }


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}