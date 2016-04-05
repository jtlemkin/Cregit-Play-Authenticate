begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|module
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
name|mail
operator|.
name|Mailer
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
name|mail
operator|.
name|MailerImpl
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
name|mail
operator|.
name|MailerImpl
operator|.
name|MailerFactory
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
name|Resolver
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
name|openid
operator|.
name|OpenIdAuthProvider
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
name|AbstractModule
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
name|assistedinject
operator|.
name|FactoryModuleBuilder
import|;
end_import

begin_import
import|import
name|play
operator|.
name|api
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|play
operator|.
name|api
operator|.
name|Environment
import|;
end_import

begin_import
import|import
name|play
operator|.
name|api
operator|.
name|inject
operator|.
name|Binding
import|;
end_import

begin_import
import|import
name|play
operator|.
name|api
operator|.
name|inject
operator|.
name|Module
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|MyStupidBasicAuthProvider
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
import|;
end_import

begin_import
import|import
name|scala
operator|.
name|collection
operator|.
name|Seq
import|;
end_import

begin_import
import|import
name|service
operator|.
name|DataInitializer
import|;
end_import

begin_import
import|import
name|service
operator|.
name|MyResolver
import|;
end_import

begin_import
import|import
name|service
operator|.
name|MyUserService
import|;
end_import

begin_comment
comment|/**  * Initial DI module.  */
end_comment

begin_class
specifier|public
class|class
name|MyModule
extends|extends
name|AbstractModule
block|{
annotation|@
name|Override
specifier|protected
name|void
name|configure
parameter_list|()
block|{
name|install
argument_list|(
operator|new
name|FactoryModuleBuilder
argument_list|()
operator|.
name|implement
argument_list|(
name|Mailer
operator|.
name|class
argument_list|,
name|MailerImpl
operator|.
name|class
argument_list|)
operator|.
name|build
argument_list|(
name|MailerFactory
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|bind
argument_list|(
name|Resolver
operator|.
name|class
argument_list|)
operator|.
name|to
argument_list|(
name|MyResolver
operator|.
name|class
argument_list|)
expr_stmt|;
name|bind
argument_list|(
name|DataInitializer
operator|.
name|class
argument_list|)
operator|.
name|asEagerSingleton
argument_list|()
expr_stmt|;
name|bind
argument_list|(
name|MyUserService
operator|.
name|class
argument_list|)
operator|.
name|asEagerSingleton
argument_list|()
expr_stmt|;
comment|//bind(GoogleAuthProvider.class).asEagerSingleton();
comment|//bind(FacebookAuthProvider.class).asEagerSingleton();
comment|//bind(FoursquareAuthProvider.class).asEagerSingleton();
name|bind
argument_list|(
name|MyUsernamePasswordAuthProvider
operator|.
name|class
argument_list|)
operator|.
name|asEagerSingleton
argument_list|()
expr_stmt|;
name|bind
argument_list|(
name|OpenIdAuthProvider
operator|.
name|class
argument_list|)
operator|.
name|asEagerSingleton
argument_list|()
expr_stmt|;
comment|//bind(TwitterAuthProvider.class).asEagerSingleton();
comment|//bind(LinkedinAuthProvider.class).asEagerSingleton();
comment|//bind(VkAuthProvider.class).asEagerSingleton();
comment|//bind(XingAuthProvider.class).asEagerSingleton();
comment|//bind(UntappdAuthProvider.class).asEagerSingleton();
comment|//bind(PocketAuthProvider.class).asEagerSingleton();
comment|//bind(GithubAuthProvider.class).asEagerSingleton();
name|bind
argument_list|(
name|MyStupidBasicAuthProvider
operator|.
name|class
argument_list|)
operator|.
name|asEagerSingleton
argument_list|()
expr_stmt|;
comment|//bind(SpnegoAuthProvider.class).asEagerSingleton();
comment|//bind(EventBriteAuthProvider.class).asEagerSingleton();
block|}
block|}
end_class

end_unit

