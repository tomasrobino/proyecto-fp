<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Plantilla principal para la transformación -->
    <xsl:output method="xml" indent="yes" />

    <!-- Plantilla para procesar el cuerpo del HTML -->
    <xsl:template match="/html">
        <vehiculos>
            <xsl:apply-templates select="//div[@class='tarjeta vehiculo-card']"/>
        </vehiculos>
    </xsl:template>

    <!-- Plantilla para extraer los datos de las tarjetas de vehículos -->
    <xsl:template match="div[@class='tarjeta vehiculo-card']">
        <vehiculo>
            <marca>
                <xsl:value-of select="h3[1]"/>
            </marca>
            <modelo>
                <xsl:value-of select="h3[2]"/>
            </modelo>
            <tipo>
                <xsl:value-of select="p[@class='vehiculo-tipo']"/>
            </tipo>
            <subtipo>
                <xsl:value-of select="@data-subtipo"/>
            </subtipo>
            <precio>
                <xsl:value-of select="p[@class='vehiculo-precio']"/>
            </precio>
        </vehiculo>
    </xsl:template>

</xsl:stylesheet>
