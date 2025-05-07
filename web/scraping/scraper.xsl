<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Hago que el output sea xml -->
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <vehiculos>
            <xsl:for-each select="//div[contains(@class, 'vehiculo-card')]">
                <vehiculo>
                    <marca>
                        <xsl:value-of select="h3[1]" />
                    </marca>
                    <modelo>
                        <xsl:value-of select="h3[2]" />
                    </modelo>
                    <tipo>
                        <xsl:value-of select="@data-tipo" />
                    </tipo>
                    <xsl:if test="@data-subtipo">
                        <subtipo>
                            <xsl:value-of select="@data-subtipo" />
                        </subtipo>
                    </xsl:if>
                    <precio>
                        <xsl:value-of select="p[contains(@class, 'vehiculo-precio')]" />
                    </precio>
                </vehiculo>
            </xsl:for-each>
        </vehiculos>
    </xsl:template>

</xsl:stylesheet>
