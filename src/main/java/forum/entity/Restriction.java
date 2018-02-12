/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.entity;


/**
 * The Enum Restriction. Define the restriction of user that can be ADMIN, BASIC or BANNED. BANNED user can't be log in and can only read
 *  the topics and comments. For unbanned must contact admin. The BASIC user can post new topics and comments, can get like/unlike to comment. For each comment only 
 *  one like/unlike. Also can update comments created by him. The ADMIN can also BANn or UNBANN the user, set the restriction to user, add tags and assign
 *  tag to each topic. Beside that admin can update tags and remove tag from topic.
 */
public enum Restriction {
	
/** The basic. */
BASIC, 
/** The banned. */
 BANNED, 
/** The admin. */
 ADMIN
}
