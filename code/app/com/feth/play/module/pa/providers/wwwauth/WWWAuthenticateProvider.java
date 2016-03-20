begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright Â© 2014 Florian Hars, nMIT Solutions GmbH  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|providers
operator|.
name|wwwauth
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
name|PlayAuthenticate
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
name|exceptions
operator|.
name|AuthException
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
name|providers
operator|.
name|AuthProvider
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
name|AuthUser
import|;
end_import

begin_import
import|import
name|play
operator|.
name|inject
operator|.
name|ApplicationLifecycle
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Controller
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Result
import|;
end_import

begin_import
import|import
name|play
operator|.
name|twirl
operator|.
name|api
operator|.
name|Content
import|;
end_import

begin_comment
comment|/** A base class for browser based authentication using the WWW-Authenticate header.  *  * This does not fully implement the usual mechanism where a whole  * site or directory is protected by one of these authentication  * mechanisms. The intended use case is that it protects just a single  * URL so that it can be used as one of play-authenticate's mechanisms.  *  * Unlike other mechanisms, it returns a formatted page on authentication  * failure, which could for example be a login form for one or more of  * the other mechanisms supported.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|WWWAuthenticateProvider
extends|extends
name|AuthProvider
block|{
specifier|public
name|WWWAuthenticateProvider
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|ApplicationLifecycle
name|lifecycle
parameter_list|)
block|{
name|super
argument_list|(
name|auth
argument_list|,
name|lifecycle
argument_list|)
expr_stmt|;
block|}
comment|/** The name of the authentication scheme 	 * 	 * @return The name of the authentication scheme, like Basic or Negotiate 	 */
specifier|protected
specifier|abstract
name|String
name|authScheme
parameter_list|()
function_decl|;
comment|/** The challenge to provide to an unauthenticated client. 	 * 	 * @param context The current request context 	 * @return The challenge string to return (without the scheme name), or null 	 */
specifier|protected
specifier|abstract
name|String
name|challenge
parameter_list|(
name|Context
name|context
parameter_list|)
function_decl|;
comment|/** Try to authenticate the incoming Request. 	 * 	 * @param response The response to the challenge (without the scheme name) 	 * @return An AuthUser or null if authentication failed 	 * @throws AuthException 	 */
specifier|protected
specifier|abstract
name|AuthUser
name|authenticateResponse
parameter_list|(
name|String
name|response
parameter_list|)
throws|throws
name|AuthException
function_decl|;
comment|/** The 401 page to return to the browser if authentication failed. 	 * 	 * This could for example be a login form that submits to another 	 * authentication method. 	 * 	 * @param context The current request context 	 * @return The formatted unauthorized page 	 */
specifier|protected
name|Content
name|unauthorized
parameter_list|(
name|Context
name|context
parameter_list|)
block|{
return|return
operator|new
name|Content
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|body
parameter_list|()
block|{
return|return
literal|"Go away, you don't exit."
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|contentType
parameter_list|()
block|{
return|return
literal|"text/plain"
return|;
block|}
block|}
return|;
block|}
specifier|private
name|Result
name|deny
parameter_list|(
name|Context
name|context
parameter_list|)
block|{
name|String
name|authChallenge
init|=
name|challenge
argument_list|(
name|context
argument_list|)
decl_stmt|;
if|if
condition|(
name|authChallenge
operator|==
literal|null
condition|)
block|{
name|authChallenge
operator|=
name|authScheme
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|authChallenge
operator|=
name|authScheme
argument_list|()
operator|+
literal|" "
operator|+
name|authChallenge
expr_stmt|;
block|}
name|context
operator|.
name|response
argument_list|()
operator|.
name|setHeader
argument_list|(
literal|"WWW-Authenticate"
argument_list|,
name|authChallenge
argument_list|)
expr_stmt|;
return|return
name|Controller
operator|.
name|unauthorized
argument_list|(
name|unauthorized
argument_list|(
name|context
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|authenticate
parameter_list|(
name|Context
name|context
parameter_list|,
name|Object
name|payload
parameter_list|)
throws|throws
name|AuthException
block|{
name|String
name|auth
init|=
name|context
operator|.
name|request
argument_list|()
operator|.
name|getHeader
argument_list|(
literal|"Authorization"
argument_list|)
decl_stmt|;
if|if
condition|(
name|auth
operator|==
literal|null
condition|)
block|{
return|return
name|deny
argument_list|(
name|context
argument_list|)
return|;
block|}
name|int
name|ix
init|=
name|auth
operator|.
name|indexOf
argument_list|(
literal|32
argument_list|)
decl_stmt|;
if|if
condition|(
name|ix
operator|==
operator|-
literal|1
operator|||
operator|!
name|authScheme
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|auth
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|ix
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|deny
argument_list|(
name|context
argument_list|)
return|;
block|}
name|AuthUser
name|user
init|=
name|authenticateResponse
argument_list|(
name|auth
operator|.
name|substring
argument_list|(
name|ix
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
block|{
return|return
name|deny
argument_list|(
name|context
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|user
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isExternal
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

