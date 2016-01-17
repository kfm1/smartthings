/**
 *  Copyright 2016
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Author: SmartThings
 *  Date: 2016.01.15
 */
 
 // To Do:  
 //     * Remove isTileCommand stuff
 //     * Figure better way of config
 // 

metadata {
	definition (name: "Fibaro RGBW", namespace: "kfm1", author: "kfm1") {
		capability "Actuator"
		capability "Switch"						//	switch					on() off()
		capability "Switch Level"				//  level					setLevel(n, n)
		capability "Refresh"					//							refresh()
		capability "Sensor"						//
        capability "Power Meter"				//	power

        //capability "Color Control"			//	hue saturation color	setHue() setSaturation() setColor()
		//capability "Color Temperature"		//

        command "redOn"
        command "redOff"
        command "greenOn"
        command "greenOff"
        command "blueOn"
        command "blueOff"
        command "whiteOn"
        command "whiteOff"
        
 		command "setRedLevel"
        command "setGreenLevel"
        command "setBlueLevel"
        command "setWhiteLevel"

		// KFM TODO: Must implement these!
		command "setRGBWLevel"					// 	(n, n, n, n)
		command "setRGBWMLevel"					//  (n, n, n, n, n)

        command "reset"

		fingerprint deviceId: "0x1101", inClusters: "0x27,0x72,0x86,0x26,0x60,0x70,0x32,0x31,0x85,0x33"
	}

	simulator {}

	tiles (scale: 2){      

		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, decoration: "flat", canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action:"switch level.setLevel"
			}
			//tileAttribute ("device.color", key: "COLOR_CONTROL") {
			//	attributeState "color", action:"setColor"
			//}
			//tileAttribute ("power", key: "SECONDARY_CONTROL") {
			//	attributeState "power", label:'${currentValue} W'
			//}
        }

        standardTile("masterSwitch", "device.switch", height: 1, width: 1, decoration: "flat", canChangeIcon: true) {
			state "on", 		label:'${name}', action:"switch.off", 	icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
			state "off", 		label:'${name}', action:"switch.on", 	icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			state "turningOn", 	label:'${name}', action:"switch.off", 	icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
			state "turningOff", label:'${name}', action:"switch.on", 	icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
        }

        valueTile("power", "device.power", height: 1, width: 2) {
        	state "power", label:'Master: ${currentValue}W'
        }  

        controlTile("masterLevel", "device.level", "slider", height: 1, width: 3, decoration: "flat") {
			state "level", action:"switch level.setLevel"
		}
        
        valueTile("masterLevelValue", "device.level", decoration: "flat", height: 1, width: 1) {
        	state "level", label:'${currentValue}%'
        }     
		
        standardTile("red", "device.red", height: 2, width: 2, decoration: "flat", canChangeIcon: true) {
            state "off", label:"R", action:"redOn", icon:"st.illuminance.illuminance.dark", backgroundColor:"#D8D8D8"
            state "on", label:"R", action:"redOff", icon:"st.illuminance.illuminance.bright", backgroundColor:"#D4A190"
        }
        controlTile("redSliderControl", "device.redLevel", "slider", height: 2, width: 3, decoration: "flat") {
			state "redLevel", action:"setRedLevel"
		}
        valueTile("redValueTile", "device.redLevel", decoration: "flat", height: 2, width: 1) {
        	state "redLevel", label:'${currentValue}%'
        }     
        
        standardTile("green", "device.green", height: 2, width: 2, inactiveLabel: false, canChangeIcon: false) {
            state "off", label:"G", action:"greenOn", icon:"st.illuminance.illuminance.dark", backgroundColor:"#D8D8D8"
            state "on", label:"G", action:"greenOff", icon:"st.illuminance.illuminance.bright", backgroundColor:"#A1D490"
        }
        controlTile("greenSliderControl", "device.greenLevel", "slider", height: 2, width: 3, inactiveLabel: false) {
			state "greenLevel", action:"setGreenLevel"
		}
        valueTile("greenValueTile", "device.greenLevel", decoration: "flat", height: 2, width: 1) {
        	state "greenLevel", label:'${currentValue}%'
        }    
        
        standardTile("blue", "device.blue", height: 2, width:2, inactiveLabel: false, canChangeIcon: false) {
            state "off", label:"B", action:"blueOn", icon:"st.illuminance.illuminance.dark", backgroundColor:"#D8D8D8"
            state "on", label:"B", action:"blueOff", icon:"st.illuminance.illuminance.bright", backgroundColor:"#90C3D4"
        }
        controlTile("blueSliderControl", "device.blueLevel", "slider", height: 2, width: 3, inactiveLabel: false) {
			state "blueLevel", action:"setBlueLevel"
		}
        valueTile("blueValueTile", "device.blueLevel", decoration: "flat", height: 2, width: 1) {
        	state "blueLevel", label:'${currentValue}%'
        }  
        
        standardTile("white", "device.white", height: 2, width: 2, inactiveLabel: false, canChangeIcon: false) {
            state "off", label:"W", action:"whiteOn", icon:"st.illuminance.illuminance.dark", backgroundColor:"#D8D8D8"
            state "on", label:"W", action:"whiteOff", icon:"st.illuminance.illuminance.bright", backgroundColor:"#FFFFFF"
        }
        controlTile("whiteSliderControl", "device.whiteLevel", "slider", height: 2, width: 3, inactiveLabel: false) {
			state "whiteLevel", action:"setWhiteLevel"
		}
        valueTile("whiteValueTile", "device.whiteLevel", decoration: "flat", height: 2, width: 1) {
        	state "whiteLevel", label:'${currentValue}%'
        }  
        
        valueTile("power", "device.power", height: 1, width: 2) {
        	state "power", label:'${currentValue} W'
        }  
		standardTile("reset", "device.reset", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:"Reset Color", action:"reset", icon:"st.lights.philips.hue-single"
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}        
    }

    main(["masterSwitch"])  // was: //main(["switch"])
	details([
    	//"switch", "levelSliderControl", "rgbSelector",
        /* "masterSwitch",*/ "power", "masterLevel", "masterLevelValue",
             "red", "redSliderControl", "redValueTile",
             "green", "greenSliderControl", "greenValueTile",
             "blue", "blueSliderControl", "blueValueTile",
             "white", "whiteSliderControl", "whiteValueTile",
             //"fireplace", "storm", "deepfade",
             //"litefade", "police",
             /* "power", */"refresh" ])
}

def installed() {
	log.info "Incoming hub command: installed():  Calling configure..."
    configure()
}

def updated() {
	log.info "Incoming hub command: updated():  Calling configure..."
    //response(refresh())
    configure()
    //getDeviceData()
}

def configure() {
	log.info "Incoming hub command: configure():"
    log.debug "Configuring device for initial use..."
    log.debug "Sending events to set all channels to level 99..."
    
    sendEvent(name: "redLevel", value: 99)
    sendEvent(name: "greenLevel", value: 99)
    sendEvent(name: "blueLevel", value: 99)
    sendEvent(name: "whiteLevel", value: 99)
    
    log.debug "Sending zwave association command... [TODO: Check necessity]"
    def cmds = []
    cmds << zwave.associationV2.associationSet(groupingIdentifier:5, nodeId:[zwaveHubNodeId]).format()
    delayBetween(cmds, 500)
}

def reset() {
	log.info "Incoming hub command: reset()"
    log.debug "Resetting color to #ffffff"
    log.warn  "TODO: Need to implement reset of white channel!"
	sendEvent(name: "color", value: "#ffffff")
	//setColorTemperature(99)
}

def refresh() {
	log.info "Incoming hub command: refresh()"
    commands([
		zwave.switchMultilevelV3.switchMultilevelGet(),
	], 1000)
}


/**
 * on() 
 * 
 *  State Transitions:
 *    * [Always] Master Level + RGBW Levels to full power
 */
def on() {
	log.info "Incoming hub command: on():"
	//sendEvent(name: "switch", value: "on")

    log.debug "Initial state:"
	_loadState()
    _printState()
    
    // Set all channels plus level to full power
    //  Alternative: Reset to previous levels, but full power is simpler for users
    //  Workflow in UI is click main on/off for full power, dive into details for all individual channel control and dimming.
    
    
    
    
    log.debug "Current program (if any): '${state.runningProgram}'"
    log.debug "previousHexLevels: '${state.previousHexLevels}'"
    log.debug "colorsAreZeros() returns '${colorsAreZeros()}'"
    if (state.runningProgram) {
    	turnProgramOn(state.runningProgram.programName, state.runningProgram.programNumber)
    } else if (state.previousHexLevels) { // && !colorsAreZeros()) {
    	log.debug "Returning to previous color levels: ${state.previousHexLevels}"
    	resetToPreviousLevels(state.previousHexLevels)
    } else {
		log.warn "Could not find previous program or color levels. Resetting to defaults..."
        delayBetween(
        	[zwave.basicV1.basicSet(value: 0xFF).format(), 
    		zwave.switchMultilevelV1.switchMultilevelGet().format(),
            configure(),
            getDeviceData()], 
        5000) 
    }
}

def off() {
	log.info "Incoming hub command: off():"
	
	log.debug "Initial state:"    
    _loadState()
    _printState()
    
    // For now, just turn switch off
    
    
    
    
    // KFM Added:  Save previous hex levels:
    saveCurrentHexLevelState()
    
    sendEvent(name: "switch", value: "off", displayed: true, isStateChange: true)
    //toggleOffProgramTiles("allOfThem")
    //toggleOffColorTiles()
    
	delayBetween (
    	[zwave.basicV1.basicSet(value: 0x00).format(), 
        zwave.switchMultilevelV1.switchMultilevelGet().format()], 
    5000)
}

def setLevel(level) {
	setLevel(level, 1)
}

def setLevel(level, duration) {
	log.info "Incoming hub command: setLevel(level='${level}', dimmingDuration='${duration}')"
	if (level > 99) level = 99
	commands([
		zwave.switchMultilevelV3.switchMultilevelSet(value: level, dimmingDuration: duration),
		zwave.switchMultilevelV3.switchMultilevelGet(),
	], (durationInSeconds && durationInSeconds < 12) ? (duration * 1000) : 3500)
}

def setSaturation(percent) {
	log.info "Incoming hub command: setSaturation(percent='${percent}')"
    log.debug "Handing saturation off to setColor()..."
	setColor(saturation: percent)
}

def setHue(value) {
	log.info "Incoming hub command: setHue(value='$value')"
    log.debug "Handing hue off to setColor()..."
	setColor(hue: value)
}

def setColor(value) {
	log.info "Incoming hub command: setColor(value='${value}')"

	def result = []
        
	if (value.hex) {
    	log.debug "Setting color using RGB hex values..."
		def c = value.hex.findAll(/[0-9a-fA-F]{2}/).collect { Integer.parseInt(it, 16) }
        result << zwave.switchColorV3.switchColorSet(red:c[0], green:c[1], blue:c[2])
	} else {
		log.debug "Setting color using hue & saturation..."
        def hue = value.hue ?: device.currentValue("hue")
		def saturation = value.saturation ?: device.currentValue("saturation")
		if (hue == null) hue = 13
		if (saturation == null) saturation = 13
		def rgb = huesatToRGB(hue as Integer, saturation as Integer)
        def whiteValue = device.currentValue("colorTemperature")
        log.debug "Hue and saturation map to RGB 0x${rgb[0]}${rgb[1]}${rgb[2]}"
        result << zwave.switchColorV3.switchColorSet(red: rgb[0], green: rgb[1], blue: rgb[2])
	}

	if (value.hue) sendEvent(name: "hue", value: value.hue, displayed: false)
	if (value.hex) sendEvent(name: "color", value: value.hex, displayed: false)
	if (value.switch) sendEvent(name: "switch", value: value.switch, displayed: false)
	if (value.saturation) sendEvent(name: "saturation", value: value.saturation, displayed: false)

	commands(result)
}


def redOn() {  
    log.info "Incoming hub command: redOn()"
    def color = "red"
    def value = 99
	log.debug "Setting ${color} to ${value} via setRedLevel()..."
    sendEvent(name: color, value: "on")
    sendEvent(name: "${color}Level", value: value)
    setRedLevel(value)
}

def redOff() {
    log.info "Incoming hub command: redOff()"
    def color = "red"
    def value = 0
	log.debug "Setting ${color} to ${value} via setRedLevel()..."
    sendEvent(name: color, value: "off")
    sendEvent(name: "${color}Level", value: value)
    setRedLevel(value)
}

def setRedLevel(value) {
	log.info "Incoming hub command: setRedLevel(value='${value}')"
	//toggleOffProgramTiles(value)
    def level = Math.min(value as Integer, 99)    
    level = 255 * level/99 as Integer
	log.debug "Value maps to hex ${level}"
	if ( value > 0 ) {
    	if (device.latestValue("switch") == "off") { 
        	log.debug "Switch is currently off.  Calling on() command..."
        	on()
            log.debug "Returned from on(). Continuing setRedLevel()..."
        }
        sendEvent(name: "red", value: "on")
    } else {
    	sendEvent(name: "red", value: "off")
    }
    def redLevelNew = Math.min(value as Integer, 99)
    redLevelNew = 255 * redLevelNew/99 as Integer
    def greenLevelNew = Math.min(device.latestValue("greenLevel") as Integer, 99)
    greenLevelNew = 255 * greenLevelNew/99 as Integer    
    def blueLevelNew = Math.min(device.latestValue("blueLevel") as Integer, 99)
    blueLevelNew = 255 * blueLevelNew/99 as Integer    
    def hexColorNew = "#${hex(redLevelNew)}${hex(greenLevelNew)}${hex(blueLevelNew)}"
    setColor([hex: hexColorNew.toUpperCase()])
}

def greenOn() {
    log.info "Incoming hub command: greenOn()"
    def color = "green"
    def value = 99
	log.debug "Setting ${color} to ${value} via setGreenLevel()..."
    sendEvent(name: color, value: "on")
    sendEvent(name: "${color}Level", value: value)
    setGreenLevel(value)
}

def greenOff() {
	log.info "Incoming hub command: greenOff()"
    def color = "green"
    def value = 0
	log.debug "Setting ${color} to ${value} via setGreenLevel()..."
    sendEvent(name: color, value: "off")
    setGreenLevel(value)
}

def setGreenLevel(value) {
	log.info "Incoming hub command: setGreenLevel(value='${value}')"
	//toggleOffProgramTiles(value)
    def level = Math.min(value as Integer, 99)    
    level = 255 * level/99 as Integer
	log.debug "level: ${level}"
	if ( value > 0 ) {
    	if (device.latestValue("switch") == "off") { 
        	log.debug "Switch is currently off.  Calling on() command..."
        	on()
            log.debug "Returned from on(). Continuing setGreenLevel()..."
        }
        sendEvent(name: "green", value: "on")
    } else {
    	sendEvent(name: "green", value: "off")
    }
    def redLevelNew = Math.min(device.latestValue("redLevel") as Integer, 99)
    redLevelNew = 255 * redLevelNew/99 as Integer
    def greenLevelNew = Math.min(value as Integer, 99)
    greenLevelNew = 255 * greenLevelNew/99 as Integer    
    def blueLevelNew = Math.min(device.latestValue("blueLevel") as Integer, 99)
    blueLevelNew = 255 * blueLevelNew/99 as Integer    

    def hexColorNew = "#${hex(redLevelNew)}${hex(greenLevelNew)}${hex(blueLevelNew)}"
    log.debug "New Hex Color Code: ${hexColorNew.toUpperCase()}"

    setColor([hex: hexColorNew.toUpperCase(), isTileCommand: true])     
}

def blueOn() {
	log.info "Incoming hub command: blueOn()"
    def color = "blue"
    def value = 99
	log.debug "Setting ${color} to ${value} via setBlueLevel()..."
    sendEvent(name: color, value: "on")
    setBlueLevel(value)
}

def blueOff() {
	log.info "Incoming hub command: blueOff()"
    def color = "blue"
    def value = 0
	log.debug "Setting ${color} to ${value} via setBlueLevel()..."
	sendEvent(name: color, value: "off")
    setBlueLevel(value)
}

def setBlueLevel(value) {
	log.info "Incoming hub command: setBlueLevel(value='${value}')"
	//toggleOffProgramTiles(value)
    def level = Math.min(value as Integer, 99)    
    level = 255 * level/99 as Integer
	log.debug "level: ${level}"
	if ( value > 0 ) {
    	if (device.latestValue("switch") == "off") { 
        	log.debug "Switch is currently off.  Calling on() command..."
        	//on()
            log.debug "Returned from on(). Continuing setBlueLevel()..."
        }
        sendEvent(name: "blue", value: "on") 
    } else {
    	sendEvent(name: "blue", value: "off")
    }
    def redLevelNew = Math.min(device.latestValue("redLevel") as Integer, 99)
    redLevelNew = 255 * redLevelNew/99 as Integer
    def greenLevelNew = Math.min(device.latestValue("greenLevel") as Integer, 99)
    greenLevelNew = 255 * greenLevelNew/99 as Integer    
    def blueLevelNew = Math.min(value as Integer, 99)
    blueLevelNew = 255 * blueLevelNew/99 as Integer    

    def hexColorNew = "#${hex(redLevelNew)}${hex(greenLevelNew)}${hex(blueLevelNew)}"
    log.info "New Hex Color Code: ${hexColorNew.toUpperCase()}"

    setColor([hex: hexColorNew.toUpperCase(), isTileCommand: true])       
}

def whiteOn() {
	log.info "Incoming hub command: whiteOn()"
	sendEvent(name: "white", value: "on", displayed: true, descriptionText: "White Channel is 'ON'", isStateChange: true)
    def channel = 0
	def whiteLevel = hex(255)
    //def cmd = [String.format("3305010${channel}${whiteLevel}%02X", 50)]
    //cmd
}

def whiteOff() {
	log.info "Incoming hub command: whiteOff()"
	sendEvent(name: "white", value: "off", displayed: true, descriptionText: "White Channel is 'OFF'", isStateChange: true)
	def channel = 0
	def whiteLevel = hex(0)
    setWhiteLevel(0)
    //def cmd = [String.format("3305010${channel}${whiteLevel}%02X", 50)]
    //cmd
}

def setWhiteLevel(value) {
	log.info "Incoming hub command: setWhiteLevel(value='${value}')"
    def level = Math.min(value as Integer, 99)    
    level = 255 * level/99 as Integer
	log.debug "level: ${level}"
	if ( value > 0 ) {
    	if (device.latestValue("switch") == "off") { 
        	log.debug "Switch is currently off.  Calling on() command..."
        	//on()
            log.debug "Returned from on(). Continuing setWhiteLevel()..."
        }
        sendEvent(name: "white", value: "on")
    } else {
    	sendEvent(name: "white", value: "off")
    }
    def channel = 0
	def whiteLevel = hex(level)
    def cmd = [String.format("3305010${channel}${whiteLevel}%02X", 50)]
    cmd    
}

def saveCurrentHexLevelState() {
	def c = getCurrentHexLevels()
    log.debug "Saving current light channel levels (R:${c["red"]} G:${c["green"]} B:${c["blue"]} W:${c["white"]} M:${c["master"]})"
	state.previousHexLevels = c
}


def getCurrentHexLevels() {
    def redLevelNew = Math.min(device.latestValue("redLevel") as Integer, 99)
    redLevelNew = 255 * redLevelNew/99 as Integer
    def greenLevelNew = Math.min(device.latestValue("greenLevel") as Integer, 99)
    greenLevelNew = 255 * greenLevelNew/99 as Integer    
    def blueLevelNew = Math.min(device.latestValue("blueLevel") as Integer, 99)
    blueLevelNew = 255 * blueLevelNew/99 as Integer
    def whiteLevelNew = Math.min(device.latestValue("whiteLevel") as Integer, 99)
    whiteLevelNew = 255 * whiteLevelNew/99 as Integer
    def masterLevelNew = Math.min(device.latestValue("level") as Integer, 99)
    masterLevelNew = 255 * masterLevelNew/99 as Integer
    def currentHexLevels = ["red":		hex(redLevelNew),
    				   	    "green":	hex(greenLevelNew),
                            "blue":		hex(blueLevelNew),
                            "white":	hex(whiteLevelNew),
                            "master":	hex(masterLevelNew),
                            "rgbw":		"${hex(redLevelNew)}${hex(greenLevelNew)}${hex(blueLevelNew)}${hex(whiteLevelNew)}"]                            
	return currentHexLevels
}

def resetToPreviousLevels(values) {
	log.debug "resetToPreviousLevels with ${values}"
	def hexColorPrevious = "#${values.red}${values.green}${values.blue}"
    def colorString = getColorDataFromHex(hexColorPrevious)

    if ( Integer.parseInt(values.red,16) > 0 )
    	sendEvent(name: "red", value: "on")

    if ( Integer.parseInt(values.green,16) > 0 )
    	sendEvent(name: "green", value: "on")
    
    if ( Integer.parseInt(values.blue,16) > 0 )
    	sendEvent(name: "blue", value: "on")
        
    if ( Integer.parseInt(values.white,16) > 0 )
    	sendEvent(name: "white", value: "on")
        
	//for some reason we have a race condition with setColor and setWhiteLevel. Can't have both
    //setColor(colorString)
    //set the white channel to what it was
    //setWhiteLevel(values.white)
    
    //using raw device command because of issue above. When device reports back it will set sliders
    sendRGBW(values.red,values.green,values.blue,values.white)
}


def sendRGBW(redHex, greenHex, blueHex, whiteHex) {
    def cmd = [String.format("33050400${whiteHex}02${redHex}03${greenHex}04${blueHex}%02X", 100),]
    cmd
}

def getDeviceData() {
    log.info "Incoming hub command: getDeviceData()"
    def cmd = []   
    cmd << response(zwave.manufacturerSpecificV2.manufacturerSpecificGet()) 
    cmd << response(zwave.versionV1.versionGet())
	cmd << response(zwave.firmwareUpdateMdV1.firmwareMdGet()) 
    delayBetween(cmd, 2500)
}

private dimmerEvents(physicalgraph.zwave.Command cmd) {
	log.info "Incoming ZWave Event: dimmerEvents command:"
    def value = (cmd.value ? "on" : "off")
	def result = [createEvent(name: "switch", value: value, displayed: false)]
	if (cmd.value) {
		result << createEvent(name: "level", value: cmd.value, unit: "%")
	}
	return result
}

private command(physicalgraph.zwave.Command cmd) {
	if (state.sec) {
		zwave.securityV1.securityMessageEncapsulation().encapsulate(cmd).format()
	} else {
		cmd.format()
	}
}

private commands(commands, delay=200) {
	delayBetween(commands.collect{ command(it) }, delay)
}



/****************************************************
 *
 *  Incoming ZWave Event Handling
 *
 ****************************************************/

def parse(description) {
	log.info "Incoming ZWave Event: Parsing '${description}'..."

	if (description == "updated") {
		log.debug("ZWave Event parsed as 'updated'.")
        return null
    }

	def cmd = zwave.parse(description, [0x20: 1, 0x26: 2, 0x70: 2, 0x72: 2, 0x60: 3, 0x33: 2, 0x32: 2, 0x31:2, 0x30: 2, 0x86: 1, 0x7A: 1])
	if (!cmd) {
		log.debug("Couldn't parse ZWave Event '$description'")
		return null
    }
    
	def result = zwaveEvent(cmd)
	log.debug("ZWave Event parsed to '$result'")
	return result
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCmdEncap cmd) {
	def encapsulatedCommand = cmd.encapsulatedCommand([0x20: 1, 0x26: 2, 0x30: 2, 0x31: 2, 0x32: 2, 0x33: 2, 0x70: 2]) // can specify command class versions here like in zwave.parse
	log.info ("Incoming ZWave Event: MultiChannelCmdEncap Command from endpoint #${cmd.sourceEndPoint}: '${encapsulatedCommand}'")
	if ((cmd.sourceEndPoint >= 1) && (cmd.sourceEndPoint <= 5)) { // we don't need color report
    	if ( cmd.sourceEndPoint == 2 ) {
        	sendEvent(name: "redLevel", value: encapsulatedCommand.value)
            if ( encapsulatedCommand.value > 0 ) {
            	sendEvent(name: "red", value: "on")
            } else {
            	sendEvent(name: "red", value: "off")
            }
    	}
        if ( cmd.sourceEndPoint == 3 ) {
        	sendEvent(name: "greenLevel", value: encapsulatedCommand.value)
            if ( encapsulatedCommand.value > 0 ) {
            	sendEvent(name: "green", value: "on")
            } else {
            	sendEvent(name: "green", value: "off")
            }
        }
        if ( cmd.sourceEndPoint == 4 ) {
        	sendEvent(name: "blueLevel", value: encapsulatedCommand.value)
        	if ( encapsulatedCommand.value > 0 ) {
            	sendEvent(name: "blue", value: "on")
            } else {
            	sendEvent(name: "blue", value: "off")
            }
        }
        if ( cmd.sourceEndPoint == 5 ) {
        	sendEvent(name: "whiteLevel", value: encapsulatedCommand.value)
        	if ( encapsulatedCommand.value > 0 ) {
            	sendEvent(name: "white", value: "on")
            } else {
            	sendEvent(name: "white", value: "off")
            }
        }
        def result = "Setting Color Level Sliders from Device Color Level Report"
        return result
    } else {
    	if (encapsulatedCommand) {
			zwaveEvent(encapsulatedCommand)
        }
	}
}

def zwaveEvent(physicalgraph.zwave.commands.configurationv2.ConfigurationReport cmd) {
    log.info "Incoming ZWave Event:  ConfigurationReport command"
    return "${device.displayName} parameter '${cmd.parameterNumber}' with a byte size of '${cmd.size}' is set to '${cmd.configurationValue}'"
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelStartLevelChange cmd) {
	log.info "Incoming ZWave Event:  SwitchMultilevelStartLevelChange command"
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelStopLevelChange cmd) {
    log.info "Incoming ZWave Event:  SwitchMultilevelStopLevelChange command"
	//[response(zwave.basicV1.basicGet())]
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv2.SensorMultilevelReport cmd) {
    log.info "Incoming ZWave Event:  SensorMultilevelReport command"
    def result = [:]
    if ( cmd.sensorType == 4 ) { //power level comming in
   		result.name = "power"
    	result.value = cmd.scaledSensorValue
        def plural = (result.value == 1) ? "s" : ""
    	result.descriptionText = "${device.displayName} power usage is ${result.value} watt${plural}"
        result.isStateChange
        sendEvent(name: result.name, value: result.value, displayed: false)
    }
    result
}
def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	log.info "Incoming ZWave Event:  BasicReport command"
    log.debug "Calling dimmerEvents..."
    dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	log.info "Incoming ZWave Event:  BasicSet command"
    log.debug "Calling dimmerEvents..."
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelReport cmd) {
	log.info "Incoming ZWave Event:  SwitchMultilevelReport command"
    log.debug "Calling dimmerEvents..."
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.hailv1.Hail cmd) {
	log.info "Incoming ZWave Event:  Hail command"
    response(command(zwave.switchMultilevelV1.switchMultilevelGet()))
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
	def encapsulatedCommand = cmd.encapsulatedCommand([0x20: 1, 0x84: 1])
	if (encapsulatedCommand) {
		state.sec = 1
		def result = zwaveEvent(encapsulatedCommand)
		result = result.collect {
			if (it instanceof physicalgraph.device.HubAction && !it.toString().startsWith("9881")) {
				response(cmd.CMD + "00" + it.toString())
			} else {
				it
			}
		}
		result
	}
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	def linkText = device.label ?: device.name
	[linkText: linkText, descriptionText: "$linkText: $cmd", displayed: false]
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
    updateDataValue("Manufacturer manufacturerName",	"${cmd.manufacturerName}")
	updateDataValue("Manufacturer manufacturerId", 		"${cmd.manufacturerId}")
    updateDataValue("Manufacturer productId", 			"${cmd.productId}")
    updateDataValue("Manufacturer productTypeId", 		"${cmd.productTypeId}")
    return "Received ManufacturerSpecificReport"
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd) {	
    updateDataValue("Version applicationVersion", 		"${cmd.applicationVersion}")
    updateDataValue("Version applicationSubVersion", 	"${cmd.applicationSubVersion}")
    updateDataValue("Version zWaveLibraryType", 		"${cmd.zWaveLibraryType}")
    updateDataValue("Version zWaveProtocolVersion", 	"${cmd.zWaveProtocolVersion}")
    updateDataValue("Version zWaveProtocolSubVersion",	"${cmd.zWaveProtocolSubVersion}")
    return "Received VersionReport"
}

def zwaveEvent(physicalgraph.zwave.commands.firmwareupdatemdv1.FirmwareMdReport cmd) { 
    updateDataValue("Firmware checksum",		"${cmd.checksum}")
    updateDataValue("Firmware firmwareId",		"${cmd.firmwareId}")
    updateDataValue("Firmware manufacturerId",	"${cmd.manufacturerId}")
    return "Received FirmwareMdReport"
}

def updateZwaveParam(params) {
	if ( params ) {   
        def pNumber = params.paramNumber
        def pSize	= params.size
        def pValue	= [params.value]
        log.debug "Updating ${device.displayName} parameter number '${pNumber}' with value '${pValue}' with size of '${pSize}'"
		def cmds = []
        cmds << zwave.configurationV1.configurationSet(configurationValue: pValue, parameterNumber: pNumber, size: pSize).format()        
        cmds << zwave.configurationV1.configurationGet(parameterNumber: pNumber).format()
        delayBetween(cmds, 1500)        
    }
}



/****************************************************
 *
 *  State Tracking
 *
 ****************************************************/



/**
 * Called at the beginning of a hub event.  Does these things:
 *   1. Loads current state of device
 *   2. Clears stale items, like state.nextLevels
 */
def _loadState() {
	
    // Initialize presets if none exist, default is full power
    //def channels = ["r", "g", "b", "w", "m"]
    //channels.each({val ->
    //	if (!state.presetLevels.containsKey(val)) state.presetLevels.(val) = 99	
    //})
    if (!state.presetLevels) state.presetLevels = [:]
    if (!state.presetLevels.containsKey('r')) state.presetLevels.r = 99
    if (!state.presetLevels.containsKey('g')) state.presetLevels.g = 99	
    if (!state.presetLevels.containsKey('b')) state.presetLevels.b = 99	
    if (!state.presetLevels.containsKey('w')) state.presetLevels.w = 99	
    if (!state.presetLevels.containsKey('m')) state.presetLevels.m = 99	
    
    
    // Set current levels
    state.currentLevels = [
    	r: Math.min(device.latestValue("redLevel") 		as Integer, 99),
        g: Math.min(device.latestValue("greenLevel") 	as Integer, 99),
        b: Math.min(device.latestValue("blueLevel") 	as Integer, 99),
        w: Math.min(device.latestValue("whiteLevel") 	as Integer, 99),
        m: Math.min(device.latestValue("level") 		as Integer, 99),
    ]
    
    // We don't know the nextLevels yet.
    state.nextLevels = [:]	
}

/**
 * Prints the current state to the log.
 */
def _printState() {
    log.debug "  * Device Status: Switch: ${device.latestValue('switch')} Level: ${device.latestValue('level')} Power: ${device.latestValue('power')}"
    log.debug "  * Preset   Channel Levels: ${state.presetLevels}"
    log.debug "  * Current  Channel Levels: ${state.currentLevels}"
	log.debug "  * Next     Channel Levels: ${state.nextLevels}"

}



/****************************************************
 *
 *  Utility Functions
 *
 ****************************************************/


//
//  The next few functions translate between levels, values, and hex.
//

/**
 * From a map of [r:, g:, b:, optional w:] channel levels, return hex string equivalent (6 or 8, no leading hashmark)
 */
def _rgbwLevelsToHex(levels) {
	def hexString = "${_toHexFromLevel(levels.r)}${_toHexFromLevel(levels.g)}${_toHexFromLevel(levels.b)}"
    if (levels.containsKey('w')) hexString.append(_toHexFromLevel(levels.w))
    return hexString
}

/**
 * Get RGB levels from a hex string (with optional leading hashmark).  W, if any, is ignored.
 */
def _rgbHexToLevels(hexString) {
	hexString = hexString - "#"

	def levels = [
    	r: _toLevelFromHex(hexString.substring(0,2)),
        g: _toLevelFromHex(hexString.substring(2,4)),
        b: _toLevelFromHex(hexString.substring(4,6))
    ]

	return levels
}

/**
 * Get RGBW levels from a hex string (with optional leading hashmark).  W is required.
 */
def _rgbwHexToLevels(hexString) {
	def levels = _rgbHexToLevels(hexString)

	hexString = hexString - "#"
    levels.w = _toLevelFromHex(hexString.substring(6,8))
    
    return levels
}

/**
 * Get the 0-99 level (i.e., intensity) integer from 00-FF hex.  Max is 99 (not 100)!
 */
def _toLevelFromHex(hexval, minLevel=0, maxLevel=99) {
	def value = _fromHex(hexval)
    return Math.max(minLevel, Math.min(maxLevel, 100*Math.round(value/255)))
}

/**
 * Get the 00-FF hex equivalent of the 0-99 level (i.e., intensity percentage)
 */
def _toHexFromLevel(level, width=2, maxLevel=99) {
	level = Math.min(maxLevel, level as Integer)
    return _toHex(255 * (greenLevelNew/99 as Integer))
}

/**
 * Convert a decimal value (e.g., 0 to 255) to hex with provided width.
 */
def _toHex(value, width=2) {
	def s = new BigInteger(Math.round(value).toString()).toString(16)
	while (s.size() < width) {
		s = "0" + s
	}
	return s
}

/**
 * Convert a hex value (e.g., 00 to FF) to decimal integer.
 */
def _fromHex(hexval) {
	return Integer.parseInt(hexval,16)
}







// OLD

private hex(value, width=2) {
	def s = new BigInteger(Math.round(value).toString()).toString(16)
	while (s.size() < width) {
		s = "0" + s
	}
	s
}

def rgbToHex(rgb) {
    def r = hex(rgb.r)
    def g = hex(rgb.g)
    def b = hex(rgb.b)
    def hexColor = "#${r}${g}${b}"
    
    hexColor
}






/* 
def colorNameToRgb(color) {
	final colors = [  
        [name:"red", 		r: 255, g: 0,	b: 0	],
		[name:"green", 		r: 0, 	g: 255,	b: 0	],
        [name:"blue", 		r: 0, 	g: 0,	b: 255	],
        
		[name:"cyan", 		r: 0, 	g: 255,	b: 255	],
        [name:"magenta", 	r: 255, g: 0,	b: 33	],       
        [name:"orange", 	r: 255, g: 102, b: 0	],
        
        [name:"purple", 	r: 170, g: 0,	b: 255	],
		[name:"yellow", 	r: 255, g: 160, b: 0	],
        [name:"pink",		r: 255, g: 192, b: 203  ],
        
        [name:"coldWhite", 	r: 255, g: 255, b: 255	],
        [name:"warmWhite", 	r: 255, g: 255, b: 185	]        
	]
    
    def colorData = [:]    
    colorData = colors.find { it.name == color }
    
    colorData
}

*/

def colorsAreZeros() {
	def result = true
    def redVal = 	device.latestValue("redLevel") as Integer
    def greenVal =  device.latestValue("greenLevel") as Integer
    def blueVal = 	device.latestValue("blueLevel") as Integer
    def whiteVal =  device.latestValue("whiteLevel") as Integer
    log.info "total colors = ${redVal + greenVal + blueVal + whiteVal}"
    if ( redVal + greenVal + blueVal + whiteVal )
    	result = false
    result
}

def getColorDataFromHex(colorHex) {
	log.debug "getColorDataFromHex: ${colorHex}"
    
    def colorRGB = hexToRgb(colorHex)
	def colorHSL = rgbToHSL(colorRGB)
        
    def c = [:]
    c = [h: colorHSL.h, 
    			 s: colorHSL.s, 
                 l: device.latestValue("level"), 
                 r: colorRGB.r, 
                 g: colorRGB.g,
                 b: colorRGB.b,
                 rh: hex(colorRGB.r),
                 gh: hex(colorRGB.g),
                 bh: hex(colorRGB.b),
                 hex: colorHex,
                 alpha: 1]
     
     def newValue = ["hex": c.hex, "hue": c.h, "saturation": c.s, "level": c.l, "red": c.r, "green": c.g, "blue": c.b, "alpha": c.alpha]
     return newValue
}

def rgbToHSL(rgb) {
	def r = rgb.r / 255
    def g = rgb.g / 255
    def b = rgb.b / 255
    def h = 0
    def s = 0
    def l = 0
    
    def var_min = [r,g,b].min()
    def var_max = [r,g,b].max()
    def del_max = var_max - var_min
    
    l = (var_max + var_min) / 2
    
    if (del_max == 0) {
            h = 0
            s = 0
    } else {
    	if (l < 0.5) { s = del_max / (var_max + var_min) } 
        else { s = del_max / (2 - var_max - var_min) }

        def del_r = (((var_max - r) / 6) + (del_max / 2)) / del_max
        def del_g = (((var_max - g) / 6) + (del_max / 2)) / del_max
        def del_b = (((var_max - b) / 6) + (del_max / 2)) / del_max

        if (r == var_max) { h = del_b - del_g } 
        else if (g == var_max) { h = (1 / 3) + del_r - del_b } 
        else if (b == var_max) { h = (2 / 3) + del_g - del_r }
        
		if (h < 0) { h += 1 }
        if (h > 1) { h -= 1 }
	}
    def hsl = [:]    
    hsl = [h: h * 100, s: s * 100, l: l]
    
    hsl
}

def hexToRgb(colorHex) {
	def rrInt = Integer.parseInt(colorHex.substring(1,3),16)
    def ggInt = Integer.parseInt(colorHex.substring(3,5),16)
    def bbInt = Integer.parseInt(colorHex.substring(5,7),16)
    
    def colorData = [:]
    colorData = [r: rrInt, g: ggInt, b: bbInt]
    colorData
}

def rgbToHSV(red, green, blue) {
	float r = red / 255f
	float g = green / 255f
	float b = blue / 255f
	float max = [r, g, b].max()
	float delta = max - [r, g, b].min()
	def hue = 13
	def saturation = 0
	if (max && delta) {
		saturation = 100 * delta / max
		if (r == max) {
			hue = ((g - b) / delta) * 100 / 6
		} else if (g == max) {
			hue = (2 + (b - r) / delta) * 100 / 6
		} else {
			hue = (4 + (r - g) / delta) * 100 / 6
		}
	}
	[hue: hue, saturation: saturation, value: max * 100]
}

def huesatToRGB(float hue, float sat) {
	while(hue >= 100) hue -= 100
	int h = (int)(hue / 100 * 6)
	float f = hue / 100 * 6 - h
	int p = Math.round(255 * (1 - (sat / 100)))
	int q = Math.round(255 * (1 - (sat / 100) * f))
	int t = Math.round(255 * (1 - (sat / 100) * (1 - f)))
	switch (h) {
		case 0: return [255, t, p]
		case 1: return [q, 255, p]
		case 2: return [p, 255, t]
		case 3: return [p, q, 255]
		case 4: return [t, p, 255]
		case 5: return [255, p, q]
	}
}

def adjustOutgoingHue(percent) {
	def adjusted = percent
	if (percent > 31) {
		if (percent < 63.0) {
			adjusted = percent + (7 * (percent -30 ) / 32)
		}
		else if (percent < 73.0) {
			adjusted = 69 + (5 * (percent - 62) / 10)
		}
		else {
			adjusted = percent + (2 * (100 - percent) / 28)
		}
	}
	log.info "percent: $percent, adjusted: $adjusted"
	adjusted
}

def setAdjustedColor(value) {
	if (value) {
        log.trace "setAdjustedColor: ${value}"
        def adjusted = value + [:]
        adjusted.hue = adjustOutgoingHue(value.hue)
        // Needed because color picker always sends 100
        adjusted.level = null 
        setColor(adjusted)
    }
}

/* 
def setColorTemperature(percent) {
	log.info "Incoming hub command: setColorTemperature(percent='${percent}')"
    
    if ( percent >= 1 )
    	sendEvent(name: "white", value: "onwhite", descriptionText: "White Channel is 'ON'", isStateChange: true)
    else
    	sendEvent(name: "white", value: "offwhite", descriptionText: "White Channel is 'OFF'", displayed: false, isStateChange: true)
    
	if(percent > 99) percent = 99
	int warmValue = percent * 255 / 99

    command(zwave.switchColorV3.switchColorSet(warmWhite:warmValue, coldWhite:(255 - warmValue)))
}
*/
