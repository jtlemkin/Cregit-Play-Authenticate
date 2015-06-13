begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright Â© 2014 Florian Hars, nMIT Solutions GmbH  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *	   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
operator|.
name|negotiate
package|;
end_package

begin_import
import|import
name|org
operator|.
name|ietf
operator|.
name|jgss
operator|.
name|GSSContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ietf
operator|.
name|jgss
operator|.
name|GSSCredential
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ietf
operator|.
name|jgss
operator|.
name|GSSException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ietf
operator|.
name|jgss
operator|.
name|GSSManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ietf
operator|.
name|jgss
operator|.
name|Oid
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Application
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Logger
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
name|wwwauth
operator|.
name|WWWAuthenticateProvider
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
name|com
operator|.
name|google
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|com
operator|.
name|ning
operator|.
name|http
operator|.
name|util
operator|.
name|Base64
import|;
end_import

begin_comment
comment|/** Authentication against a Windows Active Directory domain.  *  * This provider implements the minimal functionality you need if you just  * want to ensure the the request comes from a user that is authenticated in  * the domain.  *  * See the README.md in this directory for more detailed usage instructions.  */
end_comment

begin_class
specifier|public
class|class
name|SpnegoAuthProvider
extends|extends
name|WWWAuthenticateProvider
block|{
annotation|@
name|Inject
specifier|public
name|SpnegoAuthProvider
parameter_list|(
name|Application
name|app
parameter_list|)
block|{
name|super
argument_list|(
name|app
argument_list|)
expr_stmt|;
name|String
name|realm
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|REALM
argument_list|)
decl_stmt|;
name|String
name|kdc
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|KDC
argument_list|)
decl_stmt|;
if|if
condition|(
name|realm
operator|!=
literal|null
operator|&&
name|kdc
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"java.security.krb5.realm"
argument_list|,
name|realm
argument_list|)
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
literal|"java.security.krb5.kdc"
argument_list|,
name|kdc
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|realm
operator|!=
literal|null
operator|||
name|kdc
operator|!=
literal|null
condition|)
block|{
name|Logger
operator|.
name|error
argument_list|(
literal|"Both realm and kdc must be given, falling back to krb5.conf"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|final
specifier|static
name|String
name|PROVIDER_KEY
init|=
literal|"spnego"
decl_stmt|;
specifier|private
specifier|static
name|Oid
name|SPNEGO_MECH_OID
decl_stmt|;
static|static
block|{
try|try
block|{
name|SPNEGO_MECH_OID
operator|=
operator|new
name|Oid
argument_list|(
literal|"1.3.6.1.5.5.2"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|GSSException
name|e
parameter_list|)
block|{
name|Logger
operator|.
name|error
argument_list|(
literal|"SPNEGO Oid is undefined"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** The windows domain and AD controller are the Kerberos realm and KDC. 	 */
specifier|public
specifier|static
specifier|abstract
class|class
name|SettingKeys
block|{
specifier|public
specifier|static
specifier|final
name|String
name|REALM
init|=
literal|"realm"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|KDC
init|=
literal|"kdc"
decl_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|authScheme
parameter_list|()
block|{
return|return
literal|"Negotiate"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|challenge
parameter_list|(
name|Context
name|context
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
comment|/** Instantiate an AuthUser object for a successfully authenticated request. 	 * 	 * Override this in a subclass if your AuthUser needs more information than 	 * just the principal name. 	 * 	 * @param gssContext 	 * @return 	 */
specifier|protected
name|AuthUser
name|makeAuthUser
parameter_list|(
name|GSSContext
name|gssContext
parameter_list|)
block|{
try|try
block|{
return|return
operator|new
name|SpnegoAuthUser
argument_list|(
name|gssContext
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|GSSException
name|e
parameter_list|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"Error creating SpnegoAuthUser"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|AuthUser
name|authenticateResponse
parameter_list|(
name|String
name|response
parameter_list|)
throws|throws
name|AuthException
block|{
if|if
condition|(
name|response
operator|.
name|startsWith
argument_list|(
literal|"TlRMTVNTU"
argument_list|)
condition|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"Discarding deprecated NTLMSSP authentication attempt"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|byte
index|[]
name|token
init|=
name|Base64
operator|.
name|decode
argument_list|(
name|response
argument_list|)
decl_stmt|;
try|try
block|{
name|GSSManager
name|gssManager
init|=
name|GSSManager
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|GSSCredential
name|gssCred
init|=
name|gssManager
operator|.
name|createCredential
argument_list|(
literal|null
argument_list|,
name|GSSCredential
operator|.
name|DEFAULT_LIFETIME
argument_list|,
name|SPNEGO_MECH_OID
argument_list|,
name|GSSCredential
operator|.
name|ACCEPT_ONLY
argument_list|)
decl_stmt|;
name|GSSContext
name|gssContext
init|=
name|gssManager
operator|.
name|createContext
argument_list|(
name|gssCred
argument_list|)
decl_stmt|;
name|byte
index|[]
name|tokenForPeer
init|=
name|gssContext
operator|.
name|acceptSecContext
argument_list|(
name|token
argument_list|,
literal|0
argument_list|,
name|token
operator|.
name|length
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|gssContext
operator|.
name|isEstablished
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
literal|"Couldn't establish GSS context"
argument_list|)
throw|;
block|}
if|if
condition|(
name|tokenForPeer
operator|!=
literal|null
condition|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"Ignoring token for peer"
argument_list|)
expr_stmt|;
block|}
name|Logger
operator|.
name|debug
argument_list|(
literal|"Authenticated "
operator|+
name|gssContext
operator|.
name|getSrcName
argument_list|()
operator|+
literal|" with "
operator|+
name|gssContext
operator|.
name|getTargName
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|makeAuthUser
argument_list|(
name|gssContext
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|GSSException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
literal|"SPNEGO authentication failed: "
operator|+
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|PROVIDER_KEY
return|;
block|}
block|}
end_class

end_unit

