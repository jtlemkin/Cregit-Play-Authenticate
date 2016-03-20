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
name|exceptions
operator|.
name|AuthException
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Call
import|;
end_import

begin_comment
comment|/**  * Resolver abstract class. You need to provide your concrete implementation of this class in your app.  * Example:  * {@code  *  bind(Resolver.class).to(TestResolver.class)  * }  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Resolver
block|{
comment|/**      * This is the route to your login page      *      * @return      */
specifier|public
specifier|abstract
name|Call
name|login
parameter_list|()
function_decl|;
comment|/**      * Route to redirect to after authentication has been finished.      * Only used if no original URL was stored.      * If you return null here, the user will get redirected to the URL of      * the setting      * afterAuthFallback      * You can use this to redirect to an external URL for example.      *      * @return      */
specifier|public
specifier|abstract
name|Call
name|afterAuth
parameter_list|()
function_decl|;
comment|/**      * This should usually point to the route where you registered      * com.feth.play.module.pa.controllers.AuthenticateController.      * authenticate(String)      * however you might provide your own authentication implementation if      * you want to      * and point it there      *      * @param provider      *            The provider ID matching one of your registered providers      *            in play.plugins      *      * @return a Call to follow      */
specifier|public
specifier|abstract
name|Call
name|auth
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|)
function_decl|;
comment|/**      * If you set the accountAutoMerge setting to true, you might return      * null for this.      *      * @return      */
specifier|public
specifier|abstract
name|Call
name|askMerge
parameter_list|()
function_decl|;
comment|/**      * If you set the accountAutoLink setting to true, you might return null      * for this      *      * @return      */
specifier|public
specifier|abstract
name|Call
name|askLink
parameter_list|()
function_decl|;
comment|/**      * Route to redirect to after logout has been finished.      * If you return null here, the user will get redirected to the URL of      * the setting      * afterLogoutFallback      * You can use this to redirect to an external URL for example.      *      * @return      */
specifier|public
specifier|abstract
name|Call
name|afterLogout
parameter_list|()
function_decl|;
specifier|public
name|Call
name|onException
parameter_list|(
specifier|final
name|AuthException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

