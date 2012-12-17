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
name|oauth1
operator|.
name|linkedin
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|StringUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
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
name|oauth1
operator|.
name|BasicOAuth1AuthUser
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
name|oauth1
operator|.
name|OAuth1AuthInfo
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
name|BasicIdentity
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
name|EducationsIdentity
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
name|EmploymentsIdentity
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
name|PicturedIdentity
import|;
end_import

begin_class
specifier|public
class|class
name|LinkedinAuthUser
extends|extends
name|BasicOAuth1AuthUser
implements|implements
name|BasicIdentity
implements|,
name|FirstLastNameIdentity
implements|,
name|PicturedIdentity
implements|,
name|EmploymentsIdentity
implements|,
name|EducationsIdentity
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
specifier|private
specifier|static
specifier|abstract
class|class
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROFILE_IMAGE_URL
init|=
literal|"pictureUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FIRST_NAME
init|=
literal|"firstName"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|LAST_NAME
init|=
literal|"lastName"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|INDUSTRY
init|=
literal|"industry"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|POSITIONS
init|=
literal|"positions/values"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EDUCATIONS
init|=
literal|"educations/values"
decl_stmt|;
specifier|private
specifier|static
specifier|abstract
class|class
name|Education
block|{
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCHOOL_NAME
init|=
literal|"schoolName"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEGREE
init|=
literal|"degree"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|START_DATE_YEAR
init|=
literal|"startDate/year"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|END_DATE_YEAR
init|=
literal|"endDate/year"
decl_stmt|;
block|}
specifier|private
specifier|static
specifier|abstract
class|class
name|Employment
block|{
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TITLE
init|=
literal|"title"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SUMMARY
init|=
literal|"summary"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|START_DATE_MONTH
init|=
literal|"startDate/month"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|START_DATE_YEAR
init|=
literal|"startDate/year"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|END_DATE_MONTH
init|=
literal|"endDate/month"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|END_DATE_YEAR
init|=
literal|"endDate/year"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|IS_CURRENT
init|=
literal|"isCurrent"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|COMPANY_NAME
init|=
literal|"company/name"
decl_stmt|;
block|}
block|}
specifier|private
name|String
name|picture
decl_stmt|;
specifier|private
name|String
name|firstName
decl_stmt|;
specifier|private
name|String
name|lastName
decl_stmt|;
specifier|private
name|String
name|industry
decl_stmt|;
specifier|private
name|String
name|email
decl_stmt|;
specifier|private
name|List
argument_list|<
name|EmploymentInfo
argument_list|>
name|employments
decl_stmt|;
specifier|private
name|List
argument_list|<
name|EducationInfo
argument_list|>
name|educations
decl_stmt|;
specifier|public
name|LinkedinAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|nodeInfo
parameter_list|,
specifier|final
name|String
name|email
parameter_list|,
specifier|final
name|OAuth1AuthInfo
name|info
parameter_list|)
block|{
name|super
argument_list|(
name|nodeInfo
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|,
name|info
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|FIRST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|firstName
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|FIRST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|LAST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|lastName
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LAST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|PROFILE_IMAGE_URL
argument_list|)
condition|)
block|{
name|this
operator|.
name|picture
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PROFILE_IMAGE_URL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|nodeInfo
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|INDUSTRY
argument_list|)
condition|)
block|{
name|this
operator|.
name|industry
operator|=
name|nodeInfo
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|INDUSTRY
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
name|JsonNode
name|node
decl_stmt|;
if|if
condition|(
operator|(
name|node
operator|=
name|traverse
argument_list|(
name|nodeInfo
argument_list|,
name|Constants
operator|.
name|POSITIONS
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|employments
operator|=
operator|new
name|ArrayList
argument_list|<
name|EmploymentInfo
argument_list|>
argument_list|()
expr_stmt|;
name|Iterator
argument_list|<
name|JsonNode
argument_list|>
name|jn
init|=
name|node
operator|.
name|getElements
argument_list|()
decl_stmt|;
while|while
condition|(
name|jn
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|JsonNode
name|j
init|=
name|jn
operator|.
name|next
argument_list|()
decl_stmt|;
name|this
operator|.
name|employments
operator|.
name|add
argument_list|(
name|makeEmployment
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
name|node
operator|=
name|traverse
argument_list|(
name|nodeInfo
argument_list|,
name|Constants
operator|.
name|EDUCATIONS
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|educations
operator|=
operator|new
name|ArrayList
argument_list|<
name|EducationInfo
argument_list|>
argument_list|()
expr_stmt|;
name|Iterator
argument_list|<
name|JsonNode
argument_list|>
name|jn
init|=
name|node
operator|.
name|getElements
argument_list|()
decl_stmt|;
while|while
condition|(
name|jn
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|JsonNode
name|j
init|=
name|jn
operator|.
name|next
argument_list|()
decl_stmt|;
name|this
operator|.
name|educations
operator|.
name|add
argument_list|(
name|makeEducation
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|email
argument_list|)
condition|)
block|{
name|this
operator|.
name|email
operator|=
name|email
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|EducationInfo
name|makeEducation
parameter_list|(
name|JsonNode
name|node
parameter_list|)
block|{
name|String
name|id
init|=
literal|null
decl_stmt|,
name|schoolName
init|=
literal|null
decl_stmt|,
name|degree
init|=
literal|null
decl_stmt|;
name|int
name|startDateYear
init|=
literal|0
decl_stmt|,
name|endDateYear
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Education
operator|.
name|ID
argument_list|)
condition|)
block|{
name|id
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Education
operator|.
name|SCHOOL_NAME
argument_list|)
condition|)
block|{
name|schoolName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Education
operator|.
name|SCHOOL_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Education
operator|.
name|DEGREE
argument_list|)
condition|)
block|{
name|degree
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Education
operator|.
name|DEGREE
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
name|JsonNode
name|childNode
decl_stmt|;
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Education
operator|.
name|START_DATE_YEAR
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|startDateYear
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Education
operator|.
name|END_DATE_YEAR
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|endDateYear
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
return|return
operator|new
name|EducationInfo
argument_list|(
name|id
argument_list|,
name|schoolName
argument_list|,
name|degree
argument_list|,
name|startDateYear
argument_list|,
name|endDateYear
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|EmploymentInfo
name|makeEmployment
parameter_list|(
name|JsonNode
name|node
parameter_list|)
block|{
name|String
name|id
init|=
literal|null
decl_stmt|,
name|title
init|=
literal|null
decl_stmt|,
name|summary
init|=
literal|null
decl_stmt|,
name|companyName
init|=
literal|null
decl_stmt|;
name|int
name|startDateMonth
init|=
literal|0
decl_stmt|,
name|startDateYear
init|=
literal|0
decl_stmt|,
name|endDateMonth
init|=
literal|0
decl_stmt|,
name|endDateYear
init|=
literal|0
decl_stmt|;
name|boolean
name|isCurrent
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|ID
argument_list|)
condition|)
block|{
name|id
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|TITLE
argument_list|)
condition|)
block|{
name|title
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|TITLE
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|SUMMARY
argument_list|)
condition|)
block|{
name|summary
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|SUMMARY
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
name|JsonNode
name|childNode
decl_stmt|;
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Employment
operator|.
name|START_DATE_MONTH
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|startDateMonth
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Employment
operator|.
name|START_DATE_YEAR
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|startDateYear
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Employment
operator|.
name|END_DATE_MONTH
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|endDateMonth
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Employment
operator|.
name|END_DATE_YEAR
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|endDateYear
operator|=
name|childNode
operator|.
name|asInt
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|IS_CURRENT
argument_list|)
condition|)
block|{
name|isCurrent
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|Employment
operator|.
name|IS_CURRENT
argument_list|)
operator|.
name|asBoolean
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|childNode
operator|=
name|LinkedinAuthUser
operator|.
name|traverse
argument_list|(
name|node
argument_list|,
name|Constants
operator|.
name|Employment
operator|.
name|COMPANY_NAME
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|companyName
operator|=
name|childNode
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
return|return
operator|new
name|EmploymentInfo
argument_list|(
name|id
argument_list|,
name|title
argument_list|,
name|summary
argument_list|,
name|startDateMonth
argument_list|,
name|startDateYear
argument_list|,
name|endDateMonth
argument_list|,
name|endDateYear
argument_list|,
name|isCurrent
argument_list|,
name|companyName
argument_list|)
return|;
block|}
comment|/** 	 * Gets the child node from a top node, by going going down the json tree 	 * via consuming '/'s. 	 * @param topNode 	 * @return 	 */
specifier|private
specifier|static
name|JsonNode
name|traverse
parameter_list|(
name|JsonNode
name|topNode
parameter_list|,
name|String
name|childExpression
parameter_list|)
block|{
name|JsonNode
name|jsonNode
init|=
name|topNode
decl_stmt|;
name|String
index|[]
name|segments
init|=
name|childExpression
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|segment
range|:
name|segments
control|)
block|{
if|if
condition|(
name|jsonNode
operator|!=
literal|null
condition|)
block|{
name|jsonNode
operator|=
name|jsonNode
operator|.
name|get
argument_list|(
name|segment
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
return|return
name|jsonNode
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProvider
parameter_list|()
block|{
return|return
name|LinkedinAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|firstName
operator|+
literal|" "
operator|+
name|lastName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPicture
parameter_list|()
block|{
return|return
name|picture
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|firstName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|lastName
return|;
block|}
specifier|public
name|String
name|getIndustry
parameter_list|()
block|{
return|return
name|industry
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|EmploymentInfo
argument_list|>
name|getEmployments
parameter_list|()
block|{
return|return
name|employments
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|email
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|EducationInfo
argument_list|>
name|getEducations
parameter_list|()
block|{
return|return
name|educations
return|;
block|}
block|}
end_class

end_unit

