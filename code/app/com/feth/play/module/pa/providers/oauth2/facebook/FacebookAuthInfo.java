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
name|providers
operator|.
name|oauth2
operator|.
name|facebook
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|fasterxml
operator|.
name|jackson
operator|.
name|databind
operator|.
name|JsonNode
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
name|OAuth2AuthInfo
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
name|OAuth2AuthProvider
import|;
end_import

begin_import
import|import static
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
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|ACCESS_TOKEN
import|;
end_import

begin_import
import|import static
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
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|REFRESH_TOKEN
import|;
end_import

begin_class
specifier|public
class|class
name|FacebookAuthInfo
extends|extends
name|OAuth2AuthInfo
block|{
comment|/**      *      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|EXPIRES_IN
init|=
literal|"expires_in"
decl_stmt|;
specifier|public
name|FacebookAuthInfo
parameter_list|(
specifier|final
name|JsonNode
name|json
parameter_list|)
block|{
name|super
argument_list|(
name|json
operator|.
name|get
argument_list|(
name|ACCESS_TOKEN
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|,
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
name|json
operator|.
name|get
argument_list|(
name|EXPIRES_IN
argument_list|)
operator|.
name|asLong
argument_list|()
operator|*
literal|1000
argument_list|,
name|json
operator|.
name|get
argument_list|(
name|REFRESH_TOKEN
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

