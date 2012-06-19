begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|service
package|;
end_package

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|AuthUser
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|AuthUserIdentity
import|;
end_import

begin_interface
specifier|public
interface|interface
name|UserService
block|{
comment|/** 	 * Saves auth provider/id combination to a local user 	 * @param authUser 	 * @return The local identifying object or null if the user existed 	 */
specifier|public
name|Object
name|save
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
function_decl|;
comment|/** 	 * Returns the local identifying object if the auth provider/id combination has been linked to a local user account already 	 * or null if not 	 *  	 * @param identity 	 * @return 	 */
specifier|public
name|Object
name|getLocalIdentity
parameter_list|(
specifier|final
name|AuthUserIdentity
name|identity
parameter_list|)
function_decl|;
comment|/** 	 * Merges two user accounts after a login with an auth provider/id that is linked to a different account than the login from before 	 * Returns the user to generate the session information from 	 *  	 * @param newUser 	 * @param oldUser 	 * @return 	 */
specifier|public
name|AuthUser
name|merge
parameter_list|(
specifier|final
name|AuthUser
name|newUser
parameter_list|,
specifier|final
name|AuthUser
name|oldUser
parameter_list|)
function_decl|;
comment|/** 	 * Links a new account to an exsting local user. 	 * Returns the auth user to log in with 	 *  	 * @param oldUser 	 * @param newUser 	 */
specifier|public
name|AuthUser
name|link
parameter_list|(
specifier|final
name|AuthUser
name|oldUser
parameter_list|,
specifier|final
name|AuthUser
name|newUser
parameter_list|)
function_decl|;
comment|/** 	 * Gets called when a user logs in - you might make profile updates here with data coming from the login provider 	 * or bump a last-logged-in date 	 *  	 * @param knownUser 	 * @return 	 */
specifier|public
name|AuthUser
name|update
parameter_list|(
specifier|final
name|AuthUser
name|knownUser
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

