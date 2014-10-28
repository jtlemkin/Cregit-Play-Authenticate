begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_import
import|import static
name|org
operator|.
name|fest
operator|.
name|assertions
operator|.
name|Assertions
operator|.
name|assertThat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|providers
operator|.
name|oauth2
operator|.
name|facebook
operator|.
name|FacebookAuthProvider
import|;
end_import

begin_class
specifier|public
class|class
name|FacebookOAuth2PopupTest
extends|extends
name|FacebookOAuth2Test
block|{
specifier|protected
name|void
name|amendConfiguration
parameter_list|(
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|additionalConfiguration
parameter_list|)
block|{
name|super
operator|.
name|amendConfiguration
argument_list|(
name|additionalConfiguration
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
name|constructSettingKey
argument_list|(
name|FacebookAuthProvider
operator|.
name|SettingKeys
operator|.
name|DISPLAY
argument_list|)
argument_list|,
literal|"popup"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|checkLoginLayout
parameter_list|()
block|{
name|assertThat
argument_list|(
name|browser
operator|.
name|find
argument_list|(
literal|"[name='display']"
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"popup"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

