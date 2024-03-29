begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|models
package|;
end_package

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|models
operator|.
name|Permission
import|;
end_import

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|models
operator|.
name|Role
import|;
end_import

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|models
operator|.
name|Subject
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|Ebean
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|ExpressionList
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
name|password
operator|.
name|UsernamePasswordAuthUser
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
name|EmailIdentity
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
name|NameIdentity
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
name|FirstLastNameIdentity
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|Finder
import|;
end_import

begin_import
import|import
name|models
operator|.
name|TokenAction
operator|.
name|Type
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|format
operator|.
name|Formats
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|validation
operator|.
name|Constraints
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Initial version based on work by Steve Chaloner (steve@objectify.be) for  * Deadbolt2  */
end_comment

begin_class
annotation|@
name|Entity
annotation|@
name|Table
argument_list|(
name|name
operator|=
literal|"users"
argument_list|)
specifier|public
class|class
name|User
extends|extends
name|AppModel
implements|implements
name|Subject
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
annotation|@
name|Constraints
operator|.
name|Email
comment|// if you make this unique, keep in mind that users *must* merge/link their
comment|// accounts then on signup with additional providers
comment|// @Column(unique = true)
specifier|public
name|String
name|email
decl_stmt|;
specifier|public
name|String
name|name
decl_stmt|;
specifier|public
name|String
name|firstName
decl_stmt|;
specifier|public
name|String
name|lastName
decl_stmt|;
annotation|@
name|Formats
operator|.
name|DateTime
argument_list|(
name|pattern
operator|=
literal|"yyyy-MM-dd HH:mm:ss"
argument_list|)
specifier|public
name|Date
name|lastLogin
decl_stmt|;
specifier|public
name|boolean
name|active
decl_stmt|;
specifier|public
name|boolean
name|emailValidated
decl_stmt|;
annotation|@
name|ManyToMany
specifier|public
name|List
argument_list|<
name|SecurityRole
argument_list|>
name|roles
decl_stmt|;
annotation|@
name|OneToMany
argument_list|(
name|cascade
operator|=
name|CascadeType
operator|.
name|ALL
argument_list|)
specifier|public
name|List
argument_list|<
name|LinkedAccount
argument_list|>
name|linkedAccounts
decl_stmt|;
annotation|@
name|ManyToMany
specifier|public
name|List
argument_list|<
name|UserPermission
argument_list|>
name|permissions
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Finder
argument_list|<
name|Long
argument_list|,
name|User
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<>
argument_list|(
name|User
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|getIdentifier
parameter_list|()
block|{
return|return
name|Long
operator|.
name|toString
argument_list|(
name|this
operator|.
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|?
extends|extends
name|Role
argument_list|>
name|getRoles
parameter_list|()
block|{
return|return
name|this
operator|.
name|roles
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|?
extends|extends
name|Permission
argument_list|>
name|getPermissions
parameter_list|()
block|{
return|return
name|this
operator|.
name|permissions
return|;
block|}
specifier|public
specifier|static
name|boolean
name|existsByAuthUserIdentity
parameter_list|(
specifier|final
name|AuthUserIdentity
name|identity
parameter_list|)
block|{
specifier|final
name|ExpressionList
argument_list|<
name|User
argument_list|>
name|exp
decl_stmt|;
if|if
condition|(
name|identity
operator|instanceof
name|UsernamePasswordAuthUser
condition|)
block|{
name|exp
operator|=
name|getUsernamePasswordAuthUserFind
argument_list|(
operator|(
name|UsernamePasswordAuthUser
operator|)
name|identity
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|exp
operator|=
name|getAuthUserFind
argument_list|(
name|identity
argument_list|)
expr_stmt|;
block|}
return|return
name|exp
operator|.
name|query
argument_list|()
operator|.
name|findCount
argument_list|()
operator|>
literal|0
return|;
block|}
specifier|private
specifier|static
name|ExpressionList
argument_list|<
name|User
argument_list|>
name|getAuthUserFind
parameter_list|(
specifier|final
name|AuthUserIdentity
name|identity
parameter_list|)
block|{
return|return
name|find
operator|.
name|query
argument_list|()
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"active"
argument_list|,
literal|true
argument_list|)
operator|.
name|eq
argument_list|(
literal|"linkedAccounts.providerUserId"
argument_list|,
name|identity
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|eq
argument_list|(
literal|"linkedAccounts.providerKey"
argument_list|,
name|identity
operator|.
name|getProvider
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|User
name|findByAuthUserIdentity
parameter_list|(
specifier|final
name|AuthUserIdentity
name|identity
parameter_list|)
block|{
if|if
condition|(
name|identity
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|identity
operator|instanceof
name|UsernamePasswordAuthUser
condition|)
block|{
return|return
name|findByUsernamePasswordIdentity
argument_list|(
operator|(
name|UsernamePasswordAuthUser
operator|)
name|identity
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|getAuthUserFind
argument_list|(
name|identity
argument_list|)
operator|.
name|findOne
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|User
name|findByUsernamePasswordIdentity
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|identity
parameter_list|)
block|{
return|return
name|getUsernamePasswordAuthUserFind
argument_list|(
name|identity
argument_list|)
operator|.
name|findOne
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|ExpressionList
argument_list|<
name|User
argument_list|>
name|getUsernamePasswordAuthUserFind
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|identity
parameter_list|)
block|{
return|return
name|getEmailUserFind
argument_list|(
name|identity
operator|.
name|getEmail
argument_list|()
argument_list|)
operator|.
name|eq
argument_list|(
literal|"linkedAccounts.providerKey"
argument_list|,
name|identity
operator|.
name|getProvider
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|merge
parameter_list|(
specifier|final
name|User
name|otherUser
parameter_list|)
block|{
for|for
control|(
specifier|final
name|LinkedAccount
name|acc
range|:
name|otherUser
operator|.
name|linkedAccounts
control|)
block|{
name|this
operator|.
name|linkedAccounts
operator|.
name|add
argument_list|(
name|LinkedAccount
operator|.
name|create
argument_list|(
name|acc
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// do all other merging stuff here - like resources, etc.
comment|// deactivate the merged user that got added to this one
name|otherUser
operator|.
name|active
operator|=
literal|false
expr_stmt|;
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|User
index|[]
block|{
name|otherUser
block|,
name|this
block|}
argument_list|)
operator|.
name|forEach
argument_list|(
name|u
lambda|->
name|Ebean
operator|.
name|save
argument_list|(
name|u
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|User
name|create
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
specifier|final
name|User
name|user
init|=
operator|new
name|User
argument_list|()
decl_stmt|;
name|user
operator|.
name|roles
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|SecurityRole
operator|.
name|findByRoleName
argument_list|(
name|controllers
operator|.
name|Application
operator|.
name|USER_ROLE
argument_list|)
argument_list|)
expr_stmt|;
comment|// user.permissions = new ArrayList<UserPermission>();
comment|// user.permissions.add(UserPermission.findByValue("printers.edit"));
name|user
operator|.
name|active
operator|=
literal|true
expr_stmt|;
name|user
operator|.
name|lastLogin
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
name|user
operator|.
name|linkedAccounts
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|LinkedAccount
operator|.
name|create
argument_list|(
name|authUser
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|authUser
operator|instanceof
name|EmailIdentity
condition|)
block|{
specifier|final
name|EmailIdentity
name|identity
init|=
operator|(
name|EmailIdentity
operator|)
name|authUser
decl_stmt|;
comment|// Remember, even when getting them from FB& Co., emails should be
comment|// verified within the application as a security breach there might
comment|// break your security as well!
name|user
operator|.
name|email
operator|=
name|identity
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|user
operator|.
name|emailValidated
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|authUser
operator|instanceof
name|NameIdentity
condition|)
block|{
specifier|final
name|NameIdentity
name|identity
init|=
operator|(
name|NameIdentity
operator|)
name|authUser
decl_stmt|;
specifier|final
name|String
name|name
init|=
name|identity
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
block|}
if|if
condition|(
name|authUser
operator|instanceof
name|FirstLastNameIdentity
condition|)
block|{
specifier|final
name|FirstLastNameIdentity
name|identity
init|=
operator|(
name|FirstLastNameIdentity
operator|)
name|authUser
decl_stmt|;
specifier|final
name|String
name|firstName
init|=
name|identity
operator|.
name|getFirstName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|lastName
init|=
name|identity
operator|.
name|getLastName
argument_list|()
decl_stmt|;
if|if
condition|(
name|firstName
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|firstName
operator|=
name|firstName
expr_stmt|;
block|}
if|if
condition|(
name|lastName
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|lastName
operator|=
name|lastName
expr_stmt|;
block|}
block|}
name|user
operator|.
name|save
argument_list|()
expr_stmt|;
comment|// Ebean.saveManyToManyAssociations(user, "roles");
comment|// Ebean.saveManyToManyAssociations(user, "permissions");
return|return
name|user
return|;
block|}
specifier|public
specifier|static
name|void
name|merge
parameter_list|(
specifier|final
name|AuthUser
name|oldUser
parameter_list|,
specifier|final
name|AuthUser
name|newUser
parameter_list|)
block|{
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|oldUser
argument_list|)
operator|.
name|merge
argument_list|(
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|newUser
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getProviders
parameter_list|()
block|{
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|providerKeys
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|this
operator|.
name|linkedAccounts
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|LinkedAccount
name|acc
range|:
name|this
operator|.
name|linkedAccounts
control|)
block|{
name|providerKeys
operator|.
name|add
argument_list|(
name|acc
operator|.
name|providerKey
argument_list|)
expr_stmt|;
block|}
return|return
name|providerKeys
return|;
block|}
specifier|public
specifier|static
name|void
name|addLinkedAccount
parameter_list|(
specifier|final
name|AuthUser
name|oldUser
parameter_list|,
specifier|final
name|AuthUser
name|newUser
parameter_list|)
block|{
specifier|final
name|User
name|u
init|=
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|oldUser
argument_list|)
decl_stmt|;
name|u
operator|.
name|linkedAccounts
operator|.
name|add
argument_list|(
name|LinkedAccount
operator|.
name|create
argument_list|(
name|newUser
argument_list|)
argument_list|)
expr_stmt|;
name|u
operator|.
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setLastLoginDate
parameter_list|(
specifier|final
name|AuthUser
name|knownUser
parameter_list|)
block|{
specifier|final
name|User
name|u
init|=
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|knownUser
argument_list|)
decl_stmt|;
name|u
operator|.
name|lastLogin
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
name|u
operator|.
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|User
name|findByEmail
parameter_list|(
specifier|final
name|String
name|email
parameter_list|)
block|{
return|return
name|getEmailUserFind
argument_list|(
name|email
argument_list|)
operator|.
name|findOne
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|ExpressionList
argument_list|<
name|User
argument_list|>
name|getEmailUserFind
parameter_list|(
specifier|final
name|String
name|email
parameter_list|)
block|{
return|return
name|find
operator|.
name|query
argument_list|()
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"active"
argument_list|,
literal|true
argument_list|)
operator|.
name|eq
argument_list|(
literal|"email"
argument_list|,
name|email
argument_list|)
return|;
block|}
specifier|public
name|LinkedAccount
name|getAccountByProvider
parameter_list|(
specifier|final
name|String
name|providerKey
parameter_list|)
block|{
return|return
name|LinkedAccount
operator|.
name|findByProviderKey
argument_list|(
name|this
argument_list|,
name|providerKey
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|verify
parameter_list|(
specifier|final
name|User
name|unverified
parameter_list|)
block|{
comment|// You might want to wrap this into a transaction
name|unverified
operator|.
name|emailValidated
operator|=
literal|true
expr_stmt|;
name|unverified
operator|.
name|save
argument_list|()
expr_stmt|;
name|TokenAction
operator|.
name|deleteByUser
argument_list|(
name|unverified
argument_list|,
name|Type
operator|.
name|EMAIL_VERIFICATION
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|changePassword
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|,
specifier|final
name|boolean
name|create
parameter_list|)
block|{
name|LinkedAccount
name|a
init|=
name|this
operator|.
name|getAccountByProvider
argument_list|(
name|authUser
operator|.
name|getProvider
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|a
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|create
condition|)
block|{
name|a
operator|=
name|LinkedAccount
operator|.
name|create
argument_list|(
name|authUser
argument_list|)
expr_stmt|;
name|a
operator|.
name|user
operator|=
name|this
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Account not enabled for password usage"
argument_list|)
throw|;
block|}
block|}
name|a
operator|.
name|providerUserId
operator|=
name|authUser
operator|.
name|getHashedPassword
argument_list|()
expr_stmt|;
name|a
operator|.
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|resetPassword
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|,
specifier|final
name|boolean
name|create
parameter_list|)
block|{
comment|// You might want to wrap this into a transaction
name|this
operator|.
name|changePassword
argument_list|(
name|authUser
argument_list|,
name|create
argument_list|)
expr_stmt|;
name|TokenAction
operator|.
name|deleteByUser
argument_list|(
name|this
argument_list|,
name|Type
operator|.
name|PASSWORD_RESET
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

